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
public class SsaController{
	
	Logger log = LoggerFactory.getLogger(SsaController.class);
	
	@Autowired
	private HardcodedData data;
	
	@RequestMapping(value = "/participants", method = RequestMethod.GET)
	public @ResponseBody Collection<Participant> getParticipants() {
		return data.participants.values();
	}
	
	@RequestMapping(value = "/participant/{id}", method = RequestMethod.GET)
	public @ResponseBody Participant getParticipant(@PathVariable("id") long id) {
		return data.participants.get(id);
	}
	
	@RequestMapping(value = "/participant/{id}/books", method = RequestMethod.GET)
	public @ResponseBody Collection<Book> geBooks(@PathVariable("id") long id) {
		return data.participants.get(id).getBooks();
	}
	
	@RequestMapping(value = "/participant/{id}/users", method = RequestMethod.GET)
	public @ResponseBody Collection<User> getUsers(@PathVariable("id") long id) {
		return data.participants.get(id).getUsers();
	}
	
	@RequestMapping(value = "/participant/{id}/legalEntities", method = RequestMethod.GET)
	public @ResponseBody Collection<LegalEntity> getLegalEntities(@PathVariable("id") long id) {
		return data.participants.get(id).getLegalEntities();
	}
	
	@RequestMapping(value = "/participant/{id}/brokerCodes", method = RequestMethod.GET)
	public @ResponseBody Collection<BrokerCode> getBrokerCodes(@PathVariable("id") long id) {
		return data.participants.get(id).getBrokerCodes();
	}
}
