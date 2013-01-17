package com.markitserv.hawthorne.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import com.markitserv.hawthorne.actions.DescribeLegalEntities;

/**
  * @author swati.choudhari
  */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	Logger log = LoggerFactory.getLogger(DescribeLegalEntities.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
    	String username = request.getHeader("username");
    	String password = request.getHeader("psw");
    	String jSessionId = request.getHeader("jsessionid");
    	log.info("username = "+username);
		log.info("psw ="+password);
		log.info("jsessionid ="+jSessionId);
    	boolean isLoginCredentialGiven = false;

    	if((StringUtils.isBlank(username) && StringUtils.isBlank(password)) && StringUtils.isNotBlank(jSessionId)){
    		isLoginCredentialGiven = true;
    	}else if((StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) && StringUtils.isBlank(jSessionId)){
    		isLoginCredentialGiven = true;
    	}else if((StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) && StringUtils.isNotBlank(jSessionId)){
    		isLoginCredentialGiven = true;
    	}
    	if(!isLoginCredentialGiven){
    		log.info("User not valid");
    		log.info("Unauthorized: Authentication token was either missing or invalid");
    		response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication token was either missing or invalid." );
    	}
        
    }
}
