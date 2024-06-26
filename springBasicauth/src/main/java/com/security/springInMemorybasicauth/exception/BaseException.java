package com.security.springInMemorybasicauth.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseException extends RuntimeException{

    private String field;
    private String code;
    private String errorMessage;
}
