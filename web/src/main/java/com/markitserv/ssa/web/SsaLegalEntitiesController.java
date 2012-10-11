package com.markitserv.ssa.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.markitserv.ssa.res.LegalEntity;
import com.markitserv.ssa.res.User;
import com.markitserv.ssa.util.HardcodedData;
import com.markitserv.ssa.util.HttpExceptions;

@Controller
@RequestMapping(value={"/legalEntity", "/legalEntites"})
public class SsaLegalEntitiesController{
	
	Logger log = LoggerFactory.getLogger(SsaLegalEntitiesController.class);
	
	@Autowired
	private HardcodedData data;

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public @ResponseBody LegalEntity get(@PathVariable("id") long id) {
		return data.legalEntities.get(id);
	}
	
	// Request mapping of "" is intentionally not here, so that users
	// get a 404
}
