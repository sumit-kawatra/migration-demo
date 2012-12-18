package com.markitserv.hawthorne.web;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.markitserv.hawthorne.res.Book;
import com.markitserv.hawthorne.res.BrokerCode;
import com.markitserv.hawthorne.res.LegalEntity;
import com.markitserv.hawthorne.res.Participant;
import com.markitserv.hawthorne.res.User;
import com.markitserv.hawthorne.util.HardcodedData;

@Controller
@RequestMapping(value={"/participant", "/participants"})
public class HawthorneParticipantController{
	
	Logger log = LoggerFactory.getLogger(HawthorneParticipantController.class);
	
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