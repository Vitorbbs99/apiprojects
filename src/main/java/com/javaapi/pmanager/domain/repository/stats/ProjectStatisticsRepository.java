package com.javaapi.pmanager.domain.repository.stats;

import com.javaapi.pmanager.domain.entity.ProjectStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStatisticsRepository extends JpaRepository<ProjectStatistics, String> {
}
