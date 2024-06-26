package com.microservices.security.springconfigwithdbandchainoffilters.dtos;

import lombok.Data;

@Data
public class LoginUserDTO {
    private String email;
    private String name;
    private String password;
}
