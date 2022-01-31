package com.matheusjmoura.postapi.mapper;

import com.matheusjmoura.postapi.controller.request.comment.CommentUpdateRequest;
import com.matheusjmoura.postapi.entity.Comment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

    public static Comment buildCommentUpdate(CommentUpdateRequest commentUpdateRequest, Comment comment) {
        return Optional.ofNullable(commentUpdateRequest).map(request ->
            Comment.builder()
                .id(comment.getId())
                .text(request.getText())
                .date(comment.getDate())
                .userId(comment.getUserId())
                .postId(comment.getPostId())
                .build()
        ).orElse(null);
    }

}
