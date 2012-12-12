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
import com.markitserv.ssa.res.User;
import com.markitserv.ssa.util.HardcodedData;
import com.markitserv.ssa.util.HttpExceptions;

@Controller
@RequestMapping(value = { "/participant/{partId}/brokerCode",
		"participant/{partId}/brokerCodes" })
public class SsaBrokerCodeController {

	Logger log = LoggerFactory.getLogger(SsaBrokerCodeController.class);

	@Autowired
	private HardcodedData data;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	Collection<BrokerCode> getAllForParticipant(@PathVariable("partId") long partId) {
		return data.participants.get(partId).getBrokerCodes();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public @ResponseBody
	BrokerCode get(@PathVariable("id") long id) {
		return data.brokerCodes.get(id);
	}
}