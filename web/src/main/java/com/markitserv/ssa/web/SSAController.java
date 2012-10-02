package com.markitserv.ssa.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.markitserv.ssa.res.Participant;
import com.markitserv.ssa.util.HardcodedData;

@Controller
@RequestMapping("/")
public class SSAController{
	
	Logger log = LoggerFactory.getLogger(SSAController.class);
	
	@Autowired
	private HardcodedData data;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody Map<Long, Participant> getUsers() {
		
		return data.getParticipants();
	}
	
	/*
	private void buildFakeData() {
		users = new HashMap<Long, User>();
		
		Participant companya = new Participant(1, "Some Company A");
		Participant companyb = new Participant(1, "Some Company B");
		User user1 = new User(1, "Roy", "Truelove", companya);
		User user2 = new User(2, "Jane", "Truelove", companyb);
		
		users.put(1l, user1);
		users.put(2l, user2);
	}
	*/
}
