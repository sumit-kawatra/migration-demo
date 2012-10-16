package com.markitserv.ssa.web;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;




@Controller
@RequestMapping("/")
public class SSASecurityTestController{
	
	Logger log = LoggerFactory.getLogger(SSASecurityTestController.class);
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET) 
    public @ResponseBody String secured() {
		System.out.println("in SSASecurityTestController");
		return "SECURED GET: " + new Date();
    }
	
	@RequestMapping(value = "/secured", method = RequestMethod.POST)
    public @ResponseBody String securedPOST() {
		System.out.println("in SSASecurityTestController");
		return "SECURED POST: " + new Date(); 
    }
	
}