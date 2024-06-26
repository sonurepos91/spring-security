package com.security.springInMemorybasicauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class SpringBasicauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBasicauthApplication.class, args);
	}

}
