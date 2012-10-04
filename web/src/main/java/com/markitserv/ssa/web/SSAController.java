package com.markitserv.ssa.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.markitserv.ssa.res.User;
@Controller
@RequestMapping("/")

public class SSAController {
	
	Logger log = LoggerFactory.getLogger(SSAController.class);
	
	private Map<Long, User> users;
	
	public SSAController() {
		buildFakeUsers();
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody Collection<User> getUsers() {
		log.trace("Can debug");
		log.debug("Can debug");
		log.error("Can error");
		return users.values();
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public @ResponseBody User getUser(@PathVariable long userId) {
		return users.get(userId);
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	public @ResponseBody Collection<User> updateUser(@RequestBody User user) {
		System.out.println(users.keySet());
		System.out.println(user.getId());
		if(users.containsKey(user.getId())){
			System.out.println(users.values());
			System.out.println("replacing");
			users.put(user.getId(), user);
			System.out.println(users.values());
		}
		return users.values();
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public @ResponseBody Collection<User> createUser2(@RequestBody User userNew) {
		users.put(userNew.getId(), userNew);
		return users.values();
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
	public @ResponseBody Collection<User> deleteUser(@PathVariable long userId) {
		users.remove(userId);
		return users.values();
	}
	
	private void buildFakeUsers() {
		users = new HashMap<Long, User>();
		User user1 = new User(1, "Roy", "Truelove");
		User user2 = new User(2, "Swati", "Choudhari");
		User user3 = new User(3, "Lavanya", "Sakala");
		users.put(1l, user1);
		users.put(2l, user2);
		users.put(3l, user3);
	}
}
