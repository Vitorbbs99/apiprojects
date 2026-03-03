package com.javaapi.pmanager.domain.applicationservice;

import com.javaapi.pmanager.domain.entity.Task;
import com.javaapi.pmanager.domain.events.ProjectStatsEvent;
import com.javaapi.pmanager.domain.model.ProjectStatsEventType;
import com.javaapi.pmanager.domain.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    private static final Logger log = LoggerFactory.getLogger(TaskServiceTest.class);
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testCreateProject() {
        Task taskFake = new Task();
        taskFake.setId("123456");
        taskFake.setTitle("teste");

        taskRepository.save(taskFake);

        log.info("Task created: " + taskFake);

        var evento = new ProjectStatsEvent(
                taskFake.getId(),
                ProjectStatsEventType.TASK_CREATED,
                0,
                LocalDateTime.now()
        );

        log.info("pequeno objeto: " +  String.valueOf(evento));

    }

}
