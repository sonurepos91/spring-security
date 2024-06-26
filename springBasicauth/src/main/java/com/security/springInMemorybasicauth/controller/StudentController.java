package com.security.springInMemorybasicauth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/outbound")
public class StudentController {

    @GetMapping(value = "/students")
    public ResponseEntity<String> getAllStudents(){
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
