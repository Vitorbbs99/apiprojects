package com.javaapi.pmanager.domain.applicationservice;

import com.javaapi.pmanager.domain.entity.Comment;
import com.javaapi.pmanager.domain.entity.Member;
import com.javaapi.pmanager.domain.entity.Project;
import com.javaapi.pmanager.domain.entity.Task;
import com.javaapi.pmanager.domain.exception.CommentNotFoundException;
import com.javaapi.pmanager.domain.exception.DuplicateTaskException;
import com.javaapi.pmanager.domain.exception.TaskNotFoundException;
import com.javaapi.pmanager.domain.repository.CommentRepository;
import com.javaapi.pmanager.domain.repository.MemberRepository;
import com.javaapi.pmanager.domain.repository.TaskRepository;
import com.javaapi.pmanager.infrastructure.dto.SaveCommentDataDTO;
import com.javaapi.pmanager.infrastructure.dto.SaveTaskDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Comment createComment(SaveCommentDataDTO saveCommentDataDTO) {

        Task task = taskRepository.findById(saveCommentDataDTO.taskId())
                .orElseThrow(() -> new RuntimeException("Task não encontrada"));

        Member member = memberRepository.findById(saveCommentDataDTO.memberId())
                .orElseThrow(() -> new RuntimeException("Membro não encontrado"));

        Comment comment = Comment
                .builder()
                .text(saveCommentDataDTO.text())
                .task(task)
                .assignedMember(member)
                .build();

        commentRepository.save(comment);
        log.info("Comement created: " + comment);
        return comment;
    }

    public Comment loadComment(String commentId) {
        return commentRepository
                .findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    @Transactional
    public void deleteComment(String commentId) {
        Comment comment = loadComment(commentId);
        commentRepository.delete(comment);
    }

    @Transactional
    public Comment updateComment(String commentId, SaveCommentDataDTO saveCommentDataDTO) {

        Comment comment = loadComment(commentId);

        comment.setText(saveCommentDataDTO.text());

        return comment;
    }
}
