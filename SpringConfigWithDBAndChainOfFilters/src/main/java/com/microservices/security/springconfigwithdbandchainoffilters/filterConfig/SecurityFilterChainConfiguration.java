package com.microservices.security.springconfigwithdbandchainoffilters.filterConfig;

import com.microservices.security.springconfigwithdbandchainoffilters.services.UserDetailsServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityFilterChainConfiguration {

   @Autowired
   private JwtAuthenticationFilter jwtAuthenticationFilter;

   @Autowired
   private ManageContextHolderStrategyFilter manageContextHolderStrategyFilter;

   private static final Logger log = LogManager.getLogger(SecurityFilterChainConfiguration.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Permit all requests if we don't do this only GET Request will be permitted
                .authorizeHttpRequests(authorize->authorize
                .requestMatchers(HttpMethod.POST,   "/auth/**").permitAll() // Patterns should be value at Controller Level //(1)
                .anyRequest()
                .authenticated())
                .authenticationProvider(authenticationProvider()) // Moment Authentication Object is returned by Authentication Manager //3
               // .addFilterBefore(manageContextHolderStrategyFilter, SecurityContextHolderFilter.class) // To Do : add Own SecurityContextHolderStrategy
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)// Registered Order Is Required //(2)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        log.info("AuthenticationManager created ::::::::::::::::::::::::::");
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        log.info("AuthenticationProvider created ::::::::::::::::::::::::::");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService () {
        return new UserDetailsServiceImpl();
    }
}
