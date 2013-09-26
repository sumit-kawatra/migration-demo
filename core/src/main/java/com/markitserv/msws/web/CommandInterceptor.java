package com.markitserv.msws.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.markitserv.msws.internal.Constants;
import com.markitserv.msws.internal.UuidGenerator;

/**
 * 
 * Intercepts user requests and does the initial registration before transferring
 * control flow to the Controller.
 *
 */
@Component
public class CommandInterceptor  extends HandlerInterceptorAdapter {
	
	
	@Autowired
	private UuidGenerator uuidGenerator;
	
	public void setUuidGenerator(UuidGenerator uuidGenerator) {
		this.uuidGenerator = uuidGenerator;
	}

	Logger log = LoggerFactory.getLogger(CommandInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
	    throws Exception {		
		String uuid = uuidGenerator.generateUuid();
		request.setAttribute(Constants.UUID, uuid);
		return true;
	}
	
}
