package com.microservices.security.springconfigwithdbandchainoffilters.filterConfig;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ManageContextHolderStrategyFilter implements Filter {

    private static final Logger Log = LogManager.getLogger(ManageContextHolderStrategyFilter.class);

    public static final String MODE_THREADLOCAL = "MODE_THREADLOCAL";
    public static final String MODE_INHERITABLETHREADLOCAL = "MODE_INHERITABLETHREADLOCAL";

    public static final String MODE_GLOBAL = "MODE_GLOBAL";
    @Override
    public void init (FilterConfig filterConfig) throws ServletException {
        Log.info(" ManageContextHolderStrategyFilter created  ::::::::::::::::::::  ");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter (ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        Log.info(":::::::::::::: " + "ManageContextHolderStrategyFilter triggered ");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.setProperty("spring.security.strategy",MODE_GLOBAL);
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(emptyContext);
        chain.doFilter(request,response);
    }

    @Override
    public void destroy () {
        Filter.super.destroy();
    }
}
