package com.microservices.security.springconfigwithdbandchainoffilters.filterConfig;

import com.microservices.security.springconfigwithdbandchainoffilters.entity.User;
import com.microservices.security.springconfigwithdbandchainoffilters.services.JwtService;
import com.microservices.security.springconfigwithdbandchainoffilters.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LogManager.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;



    @Override
    protected void doFilterInternal (HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.info("Inside JwtAuthenticationFilter doFilter() Method...");
        HttpServletRequest request = servletRequest;
        HttpServletResponse response = servletResponse;

        final String authHeader = request.getHeader("Authorization");
        if(authHeader==null){
            log.info("Flow To sign Up New User Or Get JWT Token For Registered User Does not Require Authentication ::::::::::::::::::::");
            filterChain.doFilter(request,response);
            return;
        }
        log.info("Flow For API which need proper Authentication ::::::::::::::::::::");
        try {
            final String jwt = authHeader.substring(7);
            log.info("JWT Token :: " + jwt);
            final String userName = jwtService.extractUserNameFromToken(jwt);
            log.info("JWT Token Extracted UserName :: " + userName);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (userName != null && authentication == null) {
                User userDetails = this.userDetailsService.loadUserByUsername(userName);

                if (jwtService.isTokenValid(userDetails, jwt)) {
                    // To DO : Decode Password coming from UserDetails Service ::
                    Authentication authObj = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "maheshUser5678" ,userDetails.getAuthorities()));
                    if(authObj.isAuthenticated())
                        log.info(":::::::: + Principal " + authObj.getPrincipal().toString());
                    SecurityContextHolder.getContext().setAuthentication(authObj);
                    log.info("SecurityContextHolderStrategy ::: " + SecurityContextHolder.getContextHolderStrategy());
                    //HttpSession session = request.getSession(true);
                    //session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
                }
            }
            filterChain.doFilter(request, response);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

}
