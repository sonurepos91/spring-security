package com.microservices.security.springconfigwithdbandchainoffilters.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ValidationFieldError {

    private String field;
    private HttpStatus code;
    private String message;

}
