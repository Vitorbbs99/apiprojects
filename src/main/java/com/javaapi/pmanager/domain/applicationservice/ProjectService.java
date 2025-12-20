package com.javaapi.pmanager.domain.applicationservice;

import com.javaapi.pmanager.domain.entity.Member;
import com.javaapi.pmanager.domain.entity.Project;
import com.javaapi.pmanager.domain.exception.DuplicateProjectException;
import com.javaapi.pmanager.domain.exception.InvalidProjectStatusExcpetion;
import com.javaapi.pmanager.domain.exception.ProjectNotFoundException;
import com.javaapi.pmanager.domain.model.ProjectStatus;
import com.javaapi.pmanager.domain.repository.MemberRepository;
import com.javaapi.pmanager.domain.repository.ProjectRepository;
import com.javaapi.pmanager.infrastructure.dto.SaveProjectDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MemberService memberService;

    @Transactional
    public Project createProject(SaveProjectDataDTO saveProjectData) {
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
        addMembersToProject(saveProjectData.getMemberIds(), project);

        log.info("Projected created: " + project);
        return project;
    }

    public Project loadProject(String projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

    @Transactional
    public void deleteProject(String projectId) {
        Project project = loadProject(projectId);
        projectRepository.delete(project);
    }

    @Transactional
    public Project updateProject(String projectId, SaveProjectDataDTO saveProjectData) {
        if (existsProjectWithName(saveProjectData.getName(), projectId)) {
            throw new DuplicateProjectException(saveProjectData.getName());
        }
        Project project = loadProject(projectId);

        project.setName(saveProjectData.getName());
        project.setDescription(saveProjectData.getDescription());
        project.setInitialDate(saveProjectData.getInitialDate());
        project.setFinalDate(saveProjectData.getFinalDate());
        project.setStatus(convertToProjectStatus(saveProjectData.getStatus()));

        addMembersToProject(saveProjectData.getMemberIds(), project);

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

    private void addMembersToProject(Set<String> memberIds, Project project) {
        List<Member> members = Optional
                .ofNullable(memberIds)
                .orElse(Set.of())
                .stream()
                .map(id -> memberService.loadMemberById(id))
                .collect(Collectors.toList());

        project.setMembers(members);
    }
}
