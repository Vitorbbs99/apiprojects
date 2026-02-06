package com.javaapi.pmanager.infrastructure.controller;

import com.javaapi.pmanager.domain.applicationservice.ProjectService;
import com.javaapi.pmanager.domain.entity.Project;
import com.javaapi.pmanager.domain.entity.User;
import com.javaapi.pmanager.infrastructure.dto.ProjectDTO;
import com.javaapi.pmanager.infrastructure.dto.ProjectStatsDTO;
import com.javaapi.pmanager.infrastructure.dto.SaveProjectDataDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.javaapi.pmanager.infrastructure.controller.RestConstants.PATH_PROJECTS;

@RestController
@RequestMapping(PATH_PROJECTS)
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ProjectRestResource {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(
            @RequestBody @Valid SaveProjectDataDTO saveProjectDataDTO,
            @AuthenticationPrincipal User currentUser
    ) {
        Project project = projectService.createProject(saveProjectDataDTO, currentUser);

        return ResponseEntity
                .created(URI.create(PATH_PROJECTS + "/" + project.getId()))
                .body(ProjectDTO.create(project));
    }

    // URL/projects/id-do-projeto
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> loadProject(@PathVariable("id") String projectId) {
        Project project =  projectService.loadProject(projectId);
        return ResponseEntity.ok(ProjectDTO.create(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable("id") String projectId,
            @AuthenticationPrincipal User currentUser
    ) {
        projectService.deleteProject(projectId, currentUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(
        @PathVariable("id") String projectId,
        @RequestBody @Valid SaveProjectDataDTO saveProjectDataDTO,
        @AuthenticationPrincipal User currentUser
    ) {
        Project project = projectService.updateProject(projectId, saveProjectDataDTO, currentUser);
        return ResponseEntity.ok(ProjectDTO.create(project));
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<ProjectStatsDTO> projectStats(@PathVariable("id") String projectId) {
        return ResponseEntity.ok(projectService.projectStats(projectId));
    }
}
