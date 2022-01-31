package com.matheusjmoura.postapi.exception;

import com.matheusjmoura.postapi.commons.exception.BusinessException;
import lombok.Getter;

import java.util.UUID;

public class CommentPermissionException extends BusinessException {

    @Getter
    private final UUID userId;

    public CommentPermissionException(String message, UUID userId) {
        super(message, userId);
        this.userId = userId;
    }
}
