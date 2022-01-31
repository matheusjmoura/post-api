package com.matheusjmoura.postapi.exception;

import com.matheusjmoura.postapi.commons.exception.BusinessException;
import lombok.Getter;

public class UserUsernameAlreadyExistException extends BusinessException {

    @Getter
    private final String username;

    public UserUsernameAlreadyExistException(String username) {
        super("user.exception.username.exists", username);
        this.username = username;
    }
}
