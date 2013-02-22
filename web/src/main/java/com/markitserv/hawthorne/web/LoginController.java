package com.markitserv.hawthorne.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	Logger log = LoggerFactory.getLogger(LoginController.class);

	// TODO why is this necessary?
	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	String validateUser(HttpServletRequest request, HttpServletResponse response) {
		return "";
	}
}