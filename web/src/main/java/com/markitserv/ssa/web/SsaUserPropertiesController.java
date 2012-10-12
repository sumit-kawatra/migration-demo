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
import com.markitserv.ssa.res.User;
import com.markitserv.ssa.res.UserBook;
import com.markitserv.ssa.util.HardcodedData;

/**
 * Vends all properties of a given user, since there are a lot of them. Might,
 * at some point, be refactored per property
 * 
 * @author roy.truelove
 * 
 */
@Controller
@RequestMapping(value = { "/participant/{partId}/user/{userId}",
		"participant/{partId}/users/{userId}" })
public class SsaUserPropertiesController {

	Logger log = LoggerFactory.getLogger(SsaUserPropertiesController.class);

	@Autowired
	private HardcodedData data;

	@RequestMapping(value = { "userBooks", "userBooks" }, method = RequestMethod.GET)
	public @ResponseBody
	Collection<UserBook> getUserBooks(@PathVariable("userId") long userId) {
		return data.users.get(userId).getUserBooks();
	}

	@RequestMapping(value = { "userBook/{id}", "userBook/{id}" }, method = RequestMethod.GET)
	public @ResponseBody
	UserBook getUserBook(@PathVariable("id") long id) {
		return data.userBooks.get(id);
	}

	// TODO every other property
}