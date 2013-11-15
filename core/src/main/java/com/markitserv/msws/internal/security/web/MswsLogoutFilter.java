package com.markitserv.msws.internal.security.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class MswsLogoutFilter extends LogoutFilter {

	Logger log = LoggerFactory.getLogger(this.getClass());

	public MswsLogoutFilter(LogoutSuccessHandler logoutSuccessHandler,
			LogoutHandler... handlers) {
		super(logoutSuccessHandler, handlers);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		super.doFilter(req, res, chain);
	}

	/**
	 * Since all logout requests come through this filter, we always require a
	 * logout
	 */
	@Override
	protected boolean requiresLogout(HttpServletRequest request,
			HttpServletResponse response) {

		return true;

	}

}
