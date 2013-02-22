package com.markitserv.hawthorne.web;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/logout")
public class LogoutController {

	Logger log = LoggerFactory.getLogger(LogoutController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	String LogoutUser(HttpServletRequest request, HttpServletResponse response) {

		String UserName = "";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// If security is enabled
		if (auth != null) {
			Principal principal = request.getUserPrincipal();
			UserName = principal.getName();
			// Performs a logout by invalidating associated session and does
			// cleaning/remove Authentication from SecurityContext
			new SecurityContextLogoutHandler().logout(request, response, auth);
		} else {// if security is disabled just invalidate session
			request.getSession().invalidate();
		}
		log.info("User " + UserName + "Logged out Successfully.");
		return "";
	}
}
