package com.markitserv.ssa.web;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
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

	@RequestMapping(value = "/secured", method = RequestMethod.GET) 
    public @ResponseBody String secured() {
		return "SECURED: " + new Date(); 
    }
	
	@RequestMapping(value = "/participant/{id}", method = RequestMethod.GET)
	public @ResponseBody Participant getParticipant(@PathVariable("id") long id, 
			@RequestHeader MultiValueMap<String,String> headers, HttpServletRequest request ) {
			
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









