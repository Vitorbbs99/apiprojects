package com.javaapi.pmanager.domain.applicationservice;

import com.javaapi.pmanager.domain.applicationservice.ports.ProjectPublisher;
import com.javaapi.pmanager.domain.entity.Project;
import com.javaapi.pmanager.domain.entity.User;
import com.javaapi.pmanager.domain.events.ProjectCreatedEvent;
import com.javaapi.pmanager.domain.events.ProjectStatsEvent;
import com.javaapi.pmanager.domain.exception.DeniedUserException;
import com.javaapi.pmanager.domain.exception.DuplicateProjectException;
import com.javaapi.pmanager.domain.exception.InvalidProjectStatusExcpetion;
import com.javaapi.pmanager.domain.exception.ProjectNotFoundException;
import com.javaapi.pmanager.domain.model.ProjectStatsEventType;
import com.javaapi.pmanager.domain.model.ProjectStatus;
import com.javaapi.pmanager.domain.model.UserRole;
import com.javaapi.pmanager.domain.repository.ProjectRepository;
import com.javaapi.pmanager.infrastructure.dto.ProjectStatsDTO;
import com.javaapi.pmanager.infrastructure.dto.SaveProjectDataDTO;
import com.javaapi.pmanager.infrastructure.services.KafkaProducerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ProjectPublisher projectPublisher;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Transactional
    public Project createProject(SaveProjectDataDTO saveProjectData, User currentUser) {
         if (currentUser.getRoles() != UserRole.ADMIN) {
            throw new DeniedUserException("ADMIN");
         }
        if (existsProjectWithName(saveProjectData.getName(), null)) {
            throw new DuplicateProjectException(saveProjectData.getName());
        }
        Project project = Project
                .builder()
                .name(saveProjectData.getName())
                .description(saveProjectData.getDescription())
                .initialDate(saveProjectData.getInitialDate())
                .finalDate(saveProjectData.getFinalDate())
                .status(ProjectStatus.PENDING)
                .build();

        projectRepository.save(project);
        Integer amountUsers = addUsersToProject(saveProjectData.getUsersIds(), project);

        // CRIAÇÃO DE OBJETO PARA FILA NO RABBITMQ
        projectPublisher.publish(new ProjectCreatedEvent(
             project.getId(),
             project.getName(),
                project.getDescription(),
                project.getInitialDate(),
                project.getFinalDate()
        ));

        //CRIAÇÃO DE OBJETO PARA TÓPICO DE ESTATISTICAS DO PROJETO
        var evento = new ProjectStatsEvent(
                project.getId(),
                ProjectStatsEventType.PROJECT_ACTION,
                amountUsers,
                LocalDateTime.now()
        );
        kafkaProducerService.sendMessage("update-project-stats", evento);

        log.info("Projected created: " + project);
        return project;
    }

    public Project loadProject(String projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

    @Transactional
    public void deleteProject(String projectId, User currentUser) {
        if (currentUser.getRoles() != UserRole.ADMIN) {
            throw new DeniedUserException("ADMIN");
        }
        Project project = loadProject(projectId);
        projectRepository.delete(project);
    }

    @Transactional
    public Project updateProject(String projectId, SaveProjectDataDTO saveProjectData, User currentUser) {
        if (currentUser.getRoles() != UserRole.ADMIN) {
            throw new DeniedUserException("ADMIN");
        }
        if (existsProjectWithName(saveProjectData.getName(), projectId)) {
            throw new DuplicateProjectException(saveProjectData.getName());
        }
        Project project = loadProject(projectId);

        project.setName(saveProjectData.getName());
        project.setDescription(saveProjectData.getDescription());
        project.setInitialDate(saveProjectData.getInitialDate());
        project.setFinalDate(saveProjectData.getFinalDate());
        project.setStatus(convertToProjectStatus(saveProjectData.getStatus()));

        Integer amountUsers = addUsersToProject(saveProjectData.getUsersIds(), project);

        var evento = new ProjectStatsEvent(
                project.getId(),
                ProjectStatsEventType.PROJECT_ACTION,
                amountUsers,
                LocalDateTime.now()
        );

        kafkaProducerService.sendMessage("update-project-stats", evento);

        return project;
    }

    private ProjectStatus convertToProjectStatus (String statusStr) {
        try {
            return ProjectStatus.valueOf(statusStr);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidProjectStatusExcpetion(statusStr);
        }
    }

    private boolean existsProjectWithName(String name, String idToExclude) {
        return projectRepository
                .findByName(name)
                .filter(p -> !Objects.equals(p.getId(), idToExclude))
                .isPresent();
    }

    private Integer addUsersToProject(Set<String> userIds, Project project) {
        List<User> users = Optional
                .ofNullable(userIds)
                .orElse(Set.of())
                .stream()
                .map(id -> userService.loadUserById(id))
                .collect(Collectors.toList());

        project.setUsers(users);

        return (users != null) ? users.size() : 0;
    }

    public ProjectStatsDTO projectStats(String projectId) {
        var stats = projectRepository.findDetailedStats(projectId);
        return new ProjectStatsDTO(stats.getTotalTasks(), stats.getPendingTasks());
    }
}
