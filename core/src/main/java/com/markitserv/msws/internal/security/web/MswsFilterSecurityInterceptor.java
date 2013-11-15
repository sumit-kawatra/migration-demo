package com.markitserv.msws.internal.security.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Ensures that given request has an authentication 
 * @author roy.truelove
 *
 */
public class MswsFilterSecurityInterceptor extends FilterSecurityInterceptor {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		super.doFilter(request, response, chain);
	}
	
	

}
