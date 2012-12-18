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

import com.markitserv.hawthorne.res.User;
import com.markitserv.hawthorne.res.UserBook;
import com.markitserv.hawthorne.util.HardcodedData;

@Controller
@RequestMapping(value={"/participant/{partId}/user", "participant/{partId}/users"})
public class HawthorneUserController{
	
	Logger log = LoggerFactory.getLogger(HawthorneUserController.class);
	
	@Autowired
	private HardcodedData data;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody Collection<User> getUsers(@PathVariable("partId") long partId) {
		return data.participants.get(partId).getUsers();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public @ResponseBody User getUser(@PathVariable("id") long id) {
		return data.users.get(id);
	}
	
	@RequestMapping(value = "{id}/userBooks", method = RequestMethod.GET)
	public @ResponseBody Collection<UserBook> getUserBooks(@PathVariable("id") long id) {
		return data.users.get(id).getUserBooks();
	}
}
