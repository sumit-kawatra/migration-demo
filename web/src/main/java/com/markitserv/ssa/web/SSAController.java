package com.markitserv.ssa.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping(value = "/participant/{id}", method = RequestMethod.GET)
	public @ResponseBody Participant getParticipant(@PathVariable("id") long id) {
		
		return data.participants.get(id);
	}
}
