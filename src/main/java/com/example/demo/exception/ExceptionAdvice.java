package com.example.demo.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handle(MethodArgumentNotValidException exception) {
        String message = this.stringify(exception);
        log.warn(message, exception);
        return this.build(HttpStatus.BAD_REQUEST, message);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handle(ConstraintViolationException exception) {
        String message = this.stringify(exception);
        log.warn(message, exception);
        return this.build(HttpStatus.BAD_REQUEST, message);
    }

    private ProblemDetail build(HttpStatus status, String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setTitle(status.getReasonPhrase());
        return problemDetail;
    }

    private String stringify(MethodArgumentNotValidException exception) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorMessageBuilder.append(fieldError.getField()).append(": ");
            errorMessageBuilder.append(fieldError.getDefaultMessage()).append(", ");
        }
        errorMessageBuilder.deleteCharAt(errorMessageBuilder.length() - 2);
        return errorMessageBuilder.toString();
    }

    private String stringify(ConstraintViolationException exception) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        for (ConstraintViolation fieldError : exception.getConstraintViolations()) {
            errorMessageBuilder.append(fieldError.getPropertyPath()).append(": ");
            errorMessageBuilder.append(fieldError.getMessage()).append(", ");
        }
        errorMessageBuilder.deleteCharAt(errorMessageBuilder.length() - 2);
        return errorMessageBuilder.toString();
    }

}
