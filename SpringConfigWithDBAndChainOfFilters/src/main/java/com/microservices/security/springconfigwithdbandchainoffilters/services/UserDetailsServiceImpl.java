package com.microservices.security.springconfigwithdbandchainoffilters.services;

import com.microservices.security.springconfigwithdbandchainoffilters.entity.User;
import com.microservices.security.springconfigwithdbandchainoffilters.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger Log = LogManager.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    /**
     *  authenticationManager.authenticate return Authentication Object in /authenticate API  call Authentication Provider,
     *  which calls UserDetailsService to validate
     *  from there only loadUserByUsername extract username from Authentication Object
     */
    public User loadUserByUsername (String username) throws UsernameNotFoundException {
        Log.info("loadUserByUsername extract username from Authentication Object which is returned by Authentication Manager : " + username);
        return userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
}
