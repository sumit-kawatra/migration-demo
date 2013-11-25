package com.markitserv.msws.internal.security.web;

import javax.annotation.PostConstruct;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class NoRedirectAuthenticationSuccessHandler extends
		SimpleUrlAuthenticationSuccessHandler {

	@PostConstruct
	public void afterPropertiesSet() {
		setRedirectStrategy(new NoRedirectStrategy());
	}
}
