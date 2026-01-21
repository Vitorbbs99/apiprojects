package com.javaapi.pmanager.domain.applicationservice;

import com.javaapi.pmanager.domain.entity.Comment;
import com.javaapi.pmanager.domain.entity.Task;
import com.javaapi.pmanager.domain.repository.CommentRepository;
import com.javaapi.pmanager.domain.repository.TaskRepository;
import com.javaapi.pmanager.infrastructure.dto.SaveCommentDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    @Transactional
    public Comment createComment(SaveCommentDataDTO saveCommentDataDTO) {

        Comment comment = Comment
                .builder()
                .text(saveCommentDataDTO.text())
                .build();

        commentRepository.save(comment);
        log.info("Comement created: " + comment);
        return comment;
    }
}
