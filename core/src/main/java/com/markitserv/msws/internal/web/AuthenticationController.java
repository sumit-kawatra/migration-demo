package com.markitserv.msws.internal.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping(value = "/")
public class AuthenticationController {
	
	Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	/**
	 * Called after a successful login.  If the user can't login, we'll never get here.
	 * Simply returns, since a successful login doesn't need a body.
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "login")
	public @ResponseBody
	String handleSuccessfulLogin(WebRequest req) {
		return "";
	}
	
	/**
	 * Called after a successful logout.  If the user can't logout, we'll never get here.
	 * Simply returns, since a successful login doesn't need a body.
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "logout")
	public @ResponseBody
	String handleSuccessfulLogout(WebRequest req) {
		return "";
	}
}