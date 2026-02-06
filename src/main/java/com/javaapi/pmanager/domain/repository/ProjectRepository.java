package com.javaapi.pmanager.domain.repository;

import com.javaapi.pmanager.domain.entity.Project;
import com.javaapi.pmanager.domain.repository.stats.ProjectStatsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

    Optional<Project> findByName(String name);

    @Query("SELECT COUNT(t.id) as totalTasks, " +
            "SUM(CASE WHEN t.status = 'PENDING' THEN 1 ELSE 0 END) as pendingTasks " +
            "FROM Task t WHERE t.project.id = :projectId")
    ProjectStatsProjection findDetailedStats(@Param("projectId") String projectId);
}
