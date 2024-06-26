package com.microservices.security.springconfigwithdbandchainoffilters.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorType {
    USER_UNAUTHORISED(HttpStatus.FORBIDDEN,"User Not Authorised"),
    USER_UNAUTHENTICATED(HttpStatus.UNAUTHORIZED,"User Not Authenticated");

    private HttpStatus code;
    private String message;
}
