package com.markitserv.ssa.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")

public class SSAController {

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody List<String> getUsers() {
		
		Stack<String> users = new Stack<String>();
		users.push("Person A");
		users.push("Person B");
		
		return users;
		
	}
}
