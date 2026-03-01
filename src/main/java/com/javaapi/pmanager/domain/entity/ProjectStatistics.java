package com.javaapi.pmanager.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_statistics")
@Getter
public class ProjectStatistics {

    @Id
    private String projectId;

    private long totalTasks;
    private long totalUsers;
    private long completedTasks;
    private LocalDateTime lastUpdate;

    public ProjectStatistics() {}

    public ProjectStatistics(String projectId) {
        this.projectId=projectId;
        this.totalTasks=0;
        this.totalUsers=0;
        this.completedTasks=0;
        this.lastUpdate=LocalDateTime.now();
    }

    public void incrementTask() {
        this.totalTasks++;
        this.lastUpdate = LocalDateTime.now();
    }

    public void incrementUser(Integer amountUsers) {
        this.totalUsers=amountUsers;
        this.lastUpdate = LocalDateTime.now();
    }


}
