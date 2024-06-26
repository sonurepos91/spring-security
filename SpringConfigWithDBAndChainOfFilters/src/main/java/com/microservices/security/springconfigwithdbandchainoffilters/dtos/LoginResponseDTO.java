package com.microservices.security.springconfigwithdbandchainoffilters.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class LoginResponseDTO {

    private String token;
    private LocalDateTime expirationTime;
}
