package com.microservices.security.springconfigwithdbandchainoffilters.services;

import com.microservices.security.springconfigwithdbandchainoffilters.dtos.LoginUserDTO;
import com.microservices.security.springconfigwithdbandchainoffilters.dtos.RegisterUserDto;
import com.microservices.security.springconfigwithdbandchainoffilters.entity.User;
import com.microservices.security.springconfigwithdbandchainoffilters.exception.functionalException.UserNotFoundException;
import com.microservices.security.springconfigwithdbandchainoffilters.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static final Logger LOG = LogManager.getLogger(AuthenticationService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User signUpNewUser (RegisterUserDto registerUserDto) {
        User userEntity = new User();
        userEntity.setName(registerUserDto.getName());
        userEntity.setEmail(registerUserDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        return userRepository.save(userEntity);
    }

    /**
     * @param loginUserDTO AuthenticationManager Is required to Authenticate the Object
     *                     by any one Authentication Provides(in this Case One Specified In SpringSecurityFilter Chai -
     *                     and it gets verified by UserDetailsServiceImpl which implements UserDetailsService
     * @return
     */
    public User authenticate (LoginUserDTO loginUserDTO) {
        LOG.info("Authentication Service ::::::::::::::::::: ");

        Authentication authenticate = null;
        authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDTO.getName(), loginUserDTO.getPassword()));
        if (authenticate != null && authenticate.isAuthenticated()) {
            LOG.info("Authentication Object : " + authenticate);
            return userRepository.findByName(loginUserDTO.getName()).get();
        } else {
            throw new UserNotFoundException("name", "", "");
        }
    }
}
