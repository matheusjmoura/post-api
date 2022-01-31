package com.matheusjmoura.postapi.exception;

import com.matheusjmoura.postapi.commons.exception.BusinessException;
import lombok.Getter;

import java.util.UUID;

public class CommentNotRelatedException extends BusinessException {

    @Getter
    private final UUID commentId;
    @Getter
    private final UUID postId;

    public CommentNotRelatedException(UUID commentId, UUID postId) {
        super("comment.exception.notRelated", commentId, postId);
        this.postId = postId;
        this.commentId = commentId;
    }
}
