package com.matheusjmoura.postapi.exception;

import com.matheusjmoura.postapi.commons.exception.BusinessException;
import lombok.Getter;

public class UserEmailAlreadyExistException extends BusinessException {

    @Getter
    private final String email;

    public UserEmailAlreadyExistException(String email) {
        super("user.exception.email.exists", email);
        this.email = email;
    }
}
