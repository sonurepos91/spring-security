package com.microservices.security.springconfigwithdbandchainoffilters.dtos;

import lombok.Data;

@Data
public class RegisterUserDto {

    private String name;
    private String email;
    private String password;
}
