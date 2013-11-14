package com.markitserv.msws.security.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Custom auth filter. See http://stackoverflow.com/a/14735345/295797
 * 
 * @author roy.truelove
 * 
 */
public class MswsUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * This filter is only used on the login command, so authentication is
	 * always required
	 */
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request,
			HttpServletResponse response) {

		return true;

		// return (!StringUtils.isBlank(obtainUsername(request)) && !StringUtils
		// .isBlank(obtainPassword(request)));
	}

	/**
	 * By default a successful
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		User user = (User) authResult.getPrincipal();
		
		log.error("User " + user.getUsername() + " logged in successfully");
		
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		
		
		
		return super.attemptAuthentication(request, response);
	}
	
	
	
	
}
