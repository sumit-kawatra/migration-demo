package com.markitserv.ssa.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.markitserv.ssa.data.User;

@Controller
@RequestMapping("/")

public class SSAController {
	
	private Map<Long, User> users;
	
	public SSAController() {
		buildFakeUsers();
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody Collection<User> getUsers() {
		return users.values();
	}
	
	private void buildFakeUsers() {
		users = new HashMap<Long, User>();
		
		User user1 = new User(1, "Roy", "Truelove");
		User user2 = new User(2, "Jane", "Truelove");
		
		users.put(1l, user1);
		users.put(2l, user2);
	}

}
