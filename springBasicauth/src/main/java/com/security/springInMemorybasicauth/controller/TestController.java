package com.security.springInMemorybasicauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping(value = "/test")
    public ResponseEntity<String> testMethod(){
        return  ResponseEntity.ok("Test Method");
    }
}
