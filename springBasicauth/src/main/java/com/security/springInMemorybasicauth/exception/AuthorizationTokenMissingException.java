package com.security.springInMemorybasicauth.exception;

public class AuthorizationTokenMissingException extends BaseException {
    public AuthorizationTokenMissingException (String field, String code, String errorMessage) {
        super(field, code, errorMessage);
    }
}
