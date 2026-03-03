package com.javaapi.pmanager.infrastructure.messaging;

import com.javaapi.pmanager.domain.entity.ProjectStatistics;
import com.javaapi.pmanager.domain.events.ProjectStatsEvent;
import com.javaapi.pmanager.domain.model.ProjectStatsEventType;
import com.javaapi.pmanager.domain.repository.stats.ProjectStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerProjectStats {

    @Autowired
    private ProjectStatisticsRepository projectStatisticsRepository;

    @KafkaListener(topics = "update-project-stats", groupId = "pmanager-group")
    public void consume(ProjectStatsEvent event) {
        String projectId = event.projectId();

        ProjectStatistics stats = projectStatisticsRepository.findById(projectId)
                        .orElse(new ProjectStatistics(projectId));

       switch (event.type()) {
           case TASK_CREATED -> stats.incrementTask();
           case TASK_UPDATE -> stats.completeTask();
           case PROJECT_ACTION -> stats.incrementUser(event.totalUsers());
       };

        projectStatisticsRepository.save(stats);

        System.out.println("Estatísticas atualizadas para o projeto: " + projectId);
    }
}
