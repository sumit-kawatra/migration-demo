package com.markitserv.msws.internal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.markitserv.msws.internal.Constants;
import com.markitserv.msws.svc.UuidGenerator;

/**
 * 
 * Intercepts user requests and does the initial registration before
 * transferring control flow to the Controller.
 * 
 */
@Component
public class AddRequestMetadataInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UuidGenerator uuidGenerator;

	Logger log = LoggerFactory.getLogger(AddRequestMetadataInterceptor.class);

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		request.setAttribute(Constants.HTTP_ATTRIB_TIMESTAMP, new DateTime());

		String uuid = uuidGenerator.generateUuid();
		request.setAttribute(Constants.HTTP_ATTRIB_UUID, uuid);
		return true;
	}

	public void setUuidGenerator(UuidGenerator uuidGenerator) {
		this.uuidGenerator = uuidGenerator;
	}

}
