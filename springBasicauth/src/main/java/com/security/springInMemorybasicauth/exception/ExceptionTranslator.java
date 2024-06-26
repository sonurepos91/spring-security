package com.security.springInMemorybasicauth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = "com.security.springbasicauth")
public class ExceptionTranslator<T> {

    @ExceptionHandler(AuthorizationTokenMissingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<T> handleAuthorizationTokenMissingException(AuthorizationTokenMissingException ex){
        ValidationError error = new ValidationError(ex.getField(), ex.getCode(), ex.getMessage());
        return new ResponseEntity<>((T) error,HttpStatus.BAD_REQUEST);
    }
}
