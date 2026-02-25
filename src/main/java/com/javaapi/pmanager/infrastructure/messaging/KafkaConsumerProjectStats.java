package com.javaapi.pmanager.infrastructure.messaging;

import com.javaapi.pmanager.domain.entity.ProjectStatistics;
import com.javaapi.pmanager.domain.events.ProjectStatsEvent;
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

        stats.incrementTask();
        projectStatisticsRepository.save(stats);

        System.out.println("Estatísticas atualizadas para o projeto: " + projectId);
    }
}
