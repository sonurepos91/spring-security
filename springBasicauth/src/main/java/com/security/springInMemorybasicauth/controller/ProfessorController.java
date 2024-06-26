package com.security.springInMemorybasicauth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/normal/inbound")
public class ProfessorController {

    @GetMapping(value = "/professors")
    public ResponseEntity<String> getAllProfessors(){
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
