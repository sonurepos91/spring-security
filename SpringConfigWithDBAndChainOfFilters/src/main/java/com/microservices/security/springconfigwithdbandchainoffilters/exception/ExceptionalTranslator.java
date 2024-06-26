package com.microservices.security.springconfigwithdbandchainoffilters.exception;

import com.microservices.security.springconfigwithdbandchainoffilters.exception.functionalException.UserNotFoundException;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionalTranslator<T>{

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<T> handleUserNotFoundException(UserNotFoundException exception){
        ValidationFieldError validationFieldError = new ValidationFieldError(exception.getField(), ErrorType.USER_UNAUTHORISED.getCode(), ErrorType.USER_UNAUTHORISED.getMessage());
        return new ResponseEntity<>((T)validationFieldError, HttpStatus.FORBIDDEN);
    }
}
