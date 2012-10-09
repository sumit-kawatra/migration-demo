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
public class SsaController{
	
	Logger log = LoggerFactory.getLogger(SsaController.class);
	
	@Autowired
	private HardcodedData data;

	@RequestMapping(value = "/participant/{id}", method = RequestMethod.GET)
	public @ResponseBody Participant getParticipant(@PathVariable("id") long id) {
		
		Participant participant = null;
		try {
			participant = data.participants.get(id);
		} catch (Exception e) {
			// TODO handle this correctly!
			log.error("Shit went wrong", e);
		}
		return participant;
	}
}
