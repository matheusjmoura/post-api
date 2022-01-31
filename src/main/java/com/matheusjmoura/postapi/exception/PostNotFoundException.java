package com.matheusjmoura.postapi.exception;

import com.matheusjmoura.postapi.commons.exception.NotFoundException;

public class PostNotFoundException extends NotFoundException {

    public PostNotFoundException(String keyMessage) {
        super(keyMessage);
    }

    public PostNotFoundException(String keyMessage, Object... arguments) {
        super(keyMessage, arguments);
    }
}
