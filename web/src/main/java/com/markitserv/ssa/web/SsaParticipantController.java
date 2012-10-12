package com.markitserv.ssa.web;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.markitserv.ssa.res.Book;
import com.markitserv.ssa.res.BrokerCode;
import com.markitserv.ssa.res.LegalEntity;
import com.markitserv.ssa.res.Participant;
import com.markitserv.ssa.res.User;
import com.markitserv.ssa.util.HardcodedData;

@Controller
@RequestMapping(value={"/participant", "/participants"})
public class SsaParticipantController{
	
	Logger log = LoggerFactory.getLogger(SsaParticipantController.class);
	
	@Autowired
	private HardcodedData data;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody Collection<Participant> getParticipants() {
		return data.participants.values();
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public @ResponseBody Participant getParticipant(@PathVariable("id") long id) {
		return data.participants.get(id);
	}
} 