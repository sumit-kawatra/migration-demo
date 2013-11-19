package com.markitserv.msws.internal.security.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

/**
 * Our implemention of a SecurityContextLogoutHandler, which should remove the
 * authentication and invalidate the session
 * 
 * @author roy.truelove
 * 
 */
public class MswsSecurityContextLogoutHandler extends
		SecurityContextLogoutHandler {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		
		if (authentication == null) {
			//log.info("Logout called but user is not currently logged in");
		} else {
			User user = (User) authentication.getPrincipal();
			log.debug("User " + user.getUsername() + " is logging out.");
		}
		
		super.logout(request, response, authentication);

	}
}
