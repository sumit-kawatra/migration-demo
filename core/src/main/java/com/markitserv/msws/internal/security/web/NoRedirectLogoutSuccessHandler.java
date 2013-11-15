package com.markitserv.msws.internal.security.web;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class NoRedirectLogoutSuccessHandler extends
		SimpleUrlLogoutSuccessHandler {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@PostConstruct
	public void afterPropertiesSet() {
		setRedirectStrategy(new NoRedirectStrategy());
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		//log.error("Successfully logged out.");

		super.onLogoutSuccess(request, response, authentication);
	}

}
