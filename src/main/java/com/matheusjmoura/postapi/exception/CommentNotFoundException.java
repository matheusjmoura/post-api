package com.matheusjmoura.postapi.exception;

import com.matheusjmoura.postapi.commons.exception.NotFoundException;

public class CommentNotFoundException extends NotFoundException {

    public CommentNotFoundException(String keyMessage) {
        super(keyMessage);
    }

    public CommentNotFoundException(String keyMessage, Object... arguments) {
        super(keyMessage, arguments);
    }
}
