package com.security.springInMemorybasicauth.filterconfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityFilterChainConfig {

    private final Logger Log = LogManager.getLogger(org.springframework.security.web.SecurityFilterChain.class);

    @Value("${platform.admin.userName}")
    private String ADMIN_USERNAME;
    @Value("${platform.admin.password}")
    private String ADMIN_PASSWORD;

    @Value("${platform.normal.userName}")
    private String NORMAL_USERNAME;
    @Value("${platform.normal.password}")
    private String NORMAL_PASSWORD;

    @Autowired
    private Environment environment;

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

        //Only Spring Boot Starter Security
       /* http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(authorize->authorize
                        .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults());
        return http.build();*/

       http
                .csrf(csrf ->csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        // Can permitAll only GET API's  requests without disabling csrf once you have disable csrf all method types include
                        .requestMatchers("/test/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/normal/**").hasRole("NORMAL")
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    };

     @Bean
     public UserDetailsService userDetailsService () {

         UserDetails adminUser = User.builder().
                 username(environment.getProperty("platform.admin.userName")).password(passwordEncoder().encode(environment.getProperty("platform.admin.password"))).roles("ADMIN").build();
         UserDetails normalUSER = User.builder().
                 username(environment.getProperty("platform.normal.userName")).password(passwordEncoder().encode(environment.getProperty("platform.normal.password"))).roles("NORMAL").build();

         return new InMemoryUserDetailsManager(List.of(adminUser,normalUSER));
     }
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
}
