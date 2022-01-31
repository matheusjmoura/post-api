package com.matheusjmoura.postapi.commons.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionsHandler {

    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BodyError handleValidationErrors(MethodArgumentNotValidException exception, Locale locale) {
        BodyError bodyError = new BodyError(messageSource.getMessage("validation.exception.title", null, locale));
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                String message = messageSource.getMessage(Objects.requireNonNull(fieldError.getCode()),
                    fieldError.getArguments(), fieldError.getDefaultMessage(), locale);
                bodyError.addDetail(String.format("%s: %s", fieldError.getField(), message));
            } else {
                bodyError.addDetail(messageSource.getMessage(Objects.requireNonNull(error.getCode()), error.getArguments(), locale));
            }
        }
        return bodyError;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)
    public BodyError handleBusinessExceptions(BusinessException exception, Locale locale) {
        return handleExceptionMessage("business.exception.title", exception, locale);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public BodyError handleNotFoundExceptions(NotFoundException exception, Locale locale) {
        return handleExceptionMessage("notFound.exception.title", exception, locale);
    }

    private BodyError handleExceptionMessage(String keyMessage, BusinessException exception, Locale locale) {
        BodyError bodyError = new BodyError(messageSource.getMessage(keyMessage, null, locale));
        bodyError.addDetail(messageSource.getMessage(exception.getKeyMessage(), exception.getArguments(), locale));
        return bodyError;
    }
}
