package com.markitserv.hawthorne.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.markitserv.hawthorne.actions.DescribeLegalEntities;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	Logger log = LoggerFactory.getLogger(DescribeLegalEntities.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
    	String username = request.getHeader("username");
    	String password = request.getHeader("psw");
    	String usertoken = request.getHeader("usertoken");
    	log.info("username = "+username);
		log.info("psw ="+password);
		log.info("usertoken ="+usertoken);
    	boolean isLoginCredentialGiven = false;

    	if((StringUtils.isBlank(username) && StringUtils.isBlank(password)) && StringUtils.isNotBlank(usertoken)){
    		isLoginCredentialGiven = true;
    	}else if((StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) && StringUtils.isBlank(usertoken)){
    		isLoginCredentialGiven = true;
    	}else if((StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) && StringUtils.isNotBlank(usertoken)){
    		isLoginCredentialGiven = true;
    	}
    	if(!isLoginCredentialGiven){
    		log.info("User not valid");
    		log.info("Unauthorized: Authentication token was either missing or invalid");
    		response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication token was either missing or invalid." );
    	}
        
    }
}
