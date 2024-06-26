package com.microservices.security.springconfigwithdbandchainoffilters.controller;

import com.microservices.security.springconfigwithdbandchainoffilters.entity.User;
import com.microservices.security.springconfigwithdbandchainoffilters.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class NormalUserLoginController<T> {

    private static final Logger log = LogManager.getLogger(NormalUserLoginController.class);


    private final UserRepository userRepository;

    public NormalUserLoginController (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/loggedInUser",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> getAllRegisteredUsers(){
        List<User> collect = this.userRepository.findAll().parallelStream().sorted(Comparator.comparingLong(User::getId)).collect(Collectors.toList());
        List<String> users= userRepository.findAll().parallelStream().map(User::getName).sorted().collect(Collectors.toList());
        return new ResponseEntity<>((T)users, HttpStatus.OK);
    }
}
