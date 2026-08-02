package com.project.shopapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(NotFoundException.class)
    public ExceptionResponse handleNotFoundException(NotFoundException ex) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), 
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage());
    }
}
