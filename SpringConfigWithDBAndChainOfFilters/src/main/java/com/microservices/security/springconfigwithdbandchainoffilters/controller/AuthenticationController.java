package com.microservices.security.springconfigwithdbandchainoffilters.controller;

import com.microservices.security.springconfigwithdbandchainoffilters.dtos.LoginResponseDTO;
import com.microservices.security.springconfigwithdbandchainoffilters.dtos.LoginUserDTO;
import com.microservices.security.springconfigwithdbandchainoffilters.dtos.RegisterUserDto;
import com.microservices.security.springconfigwithdbandchainoffilters.entity.User;
import com.microservices.security.springconfigwithdbandchainoffilters.services.AuthenticationService;
import com.microservices.security.springconfigwithdbandchainoffilters.services.JwtService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController<T> {

    private static final Logger log = LogManager.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<T> register (@RequestBody RegisterUserDto registerUserDto) {
        log.info("Signup New user ");
        return new ResponseEntity<>((T) authenticationService.signUpNewUser(registerUserDto), HttpStatus.CREATED);
    }

    @PostMapping(value = "/authenticate",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDTO> loginRegisteredUser(@RequestBody LoginUserDTO loginUserDTO){
        log.info("Generate JWT Token for Registered User ");
    User authenticateUser= authenticationService.authenticate(loginUserDTO);
    String jwtToken = jwtService.generateToken(authenticateUser);
        log.info("Generated JWT Token for :::: " + jwtToken);
    Date date = jwtService.extractExpirationTimeFromToken(jwtToken);
    LocalDateTime localDateTime = Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return new ResponseEntity<>(LoginResponseDTO.builder().token(jwtToken).expirationTime(localDateTime).build(),HttpStatus.OK);
    }
}
