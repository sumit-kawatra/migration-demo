package com.markitserv.mwws.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.markitserv.mwws.CommonConstants;
import com.markitserv.mwws.internal.UuidGenerator;

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
	
	Logger log = LoggerFactory.getLogger(CommandInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
	    throws Exception {		
		String Uuid = uuidGenerator.generateUuid();
		request.setAttribute(CommonConstants.UUID, Uuid);
		log.info("Uuid from requestRegistry is " + Uuid);
		return true;
	}
	
}
