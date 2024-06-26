package com.microservices.security.springconfigwithdbandchainoffilters.exception.functionalException;

import com.microservices.security.springconfigwithdbandchainoffilters.exception.BaseException;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException (String field, String code, String messgae) {
        super(field, code, messgae);
    }
}
