package com.javaapi.pmanager.infrastructure.controller;

import com.javaapi.pmanager.domain.applicationservice.CommentService;
import com.javaapi.pmanager.domain.entity.Comment;
import com.javaapi.pmanager.infrastructure.dto.CommentDTO;
import com.javaapi.pmanager.infrastructure.dto.SaveCommentDataDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.javaapi.pmanager.infrastructure.controller.RestConstants.PATH_COMMENT;

@RestController
@RequestMapping(PATH_COMMENT)
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class CommentRestResource {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody @Valid SaveCommentDataDTO saveCommentDataDTO) {
        Comment comment = commentService.createComment(saveCommentDataDTO);

        return ResponseEntity
                .created(URI.create(PATH_COMMENT + "/" + comment.getId()))
                .body(CommentDTO.create(comment));
    }
}
