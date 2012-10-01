package com.markitserv.ssa.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.markitserv.ssa.res.User;

@Controller
@RequestMapping("/")

public class SSAController implements ApplicationContextAware {
	
	Logger log = LoggerFactory.getLogger(SSAController.class);
	
	private Map<Long, User> users;

	private ApplicationContext ctx;
	
	public SSAController() {
		buildFakeUsers();
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody Collection<User> getUsers() {
		
		String[] beanDefs = ctx.getBeanDefinitionNames();
		log.debug("All beans:");
		for (String def : beanDefs) {
			if (def.contains("web")) {
				log.debug(def);
			}
		}
		log.debug("END All beans:");
		return users.values();
	}
	
	private void buildFakeUsers() {
		users = new HashMap<Long, User>();
		
		User user1 = new User(1, "Roy", "Truelove");
		User user2 = new User(2, "Jane", "Truelove");
		
		users.put(1l, user1);
		users.put(2l, user2);
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		
		this.ctx = ctx;
		
	}
}
