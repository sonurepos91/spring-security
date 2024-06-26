package com.security.springInMemorybasicauth.filterconfig;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class AuthenticationFilter implements Filter {

    Logger log = LogManager.getLogger(AuthenticationFilter.class);


    @Override
    public void init (FilterConfig filterConfig) throws ServletException {
        log.info("AuthenticationFilter Chain initialised..");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter (ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        log.info("Inside AuthenticationFilter doFilter() Method...");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authorization = request.getHeader("Authorization");
        chain.doFilter(request,response);
        log.info("Response is : " + response.getStatus());
    }

    @Override
    public void destroy () {
        Filter.super.destroy();
    }
}
