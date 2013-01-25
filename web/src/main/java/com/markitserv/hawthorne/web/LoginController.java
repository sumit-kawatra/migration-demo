package com.markitserv.hawthorne.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(value="/login")
public class LoginController{
	
	Logger log = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody String validateUser() {
		System.out.println("in Login Controller");
		return "User Authenticated Successfully. Please use user token for next Request.";
	}
	

}