package com.javaapi.pmanager.domain.applicationservice;

import com.javaapi.pmanager.domain.entity.Project;
import com.javaapi.pmanager.domain.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ProjectServiceTest.class);
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void testLoadProject() {

        String id = "111";
        Project projectFake = new Project();
        projectFake.setId(id);

        when(projectRepository.findById(id)).thenReturn(Optional.of(projectFake));

        // Executar o método
        Project result = projectService.loadProject(id);

        // Verificar se funcionou
        assertNotNull(result);
        assertEquals(id, result.getId());
        log.info(result.toString());
    }
}