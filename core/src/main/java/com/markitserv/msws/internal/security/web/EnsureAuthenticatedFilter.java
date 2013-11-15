package com.markitserv.msws.internal.security.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Throws a 401 if there is no authentication.
 * @author roy.truelove
 *
 */
public class EnsureAuthenticatedFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		
		if (auth != null) {
			chain.doFilter(request, response);
		} else {
			HttpServletResponse httpResp = (HttpServletResponse)response;
			httpResp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}

	}
}
