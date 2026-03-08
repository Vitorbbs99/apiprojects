package com.javaapi.pmanager.domain.repository;

import com.javaapi.pmanager.domain.entity.Task;
import com.javaapi.pmanager.domain.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    Optional<Task> findByTitle(String title);

    @Query(
            """
            SELECT t
            FROM Task t
            WHERE
                (:status IS NULL OR t.status = :status) AND
                (:partialTitle IS NULL OR UPPER(t.title) LIKE CONCAT('%', UPPER(:partialTitle), '%'))
            """
    )
    /*List<Task> find(
            @Param("projectId") String projectId,
            @Param("memberId") String memberId,
            @Param("status") TaskStatus status,
            @Param("partialTitle") String partialTitle
    );*/
    Page<Task> find(
            @Param("status") TaskStatus status,
            @Param("partialTitle") String partialTitle,
            Pageable pageable
    );

}
