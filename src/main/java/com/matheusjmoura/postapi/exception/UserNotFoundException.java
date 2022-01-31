package com.matheusjmoura.postapi.exception;

import com.matheusjmoura.postapi.commons.exception.NotFoundException;
import lombok.Getter;

import java.util.UUID;

public class UserNotFoundException extends NotFoundException {

    @Getter
    private final UUID userId;

    public UserNotFoundException(UUID userId) {
        super("user.exception.notFound", userId);
        this.userId = userId;
    }
}
