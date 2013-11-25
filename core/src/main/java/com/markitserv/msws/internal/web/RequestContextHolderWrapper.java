package com.markitserv.msws.internal.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Spring uses 'RequestContextHolder' to get request information for a current
 * session The problem is that these are static classes, and so are not
 * mockable. This class wraps the static classes so they it can be mocked. Note
 * that not all RequestContextHolder methods are implemented, only the ones we
 * use. If you need more, pls add them
 * 
 * @author roy.truelove
 * 
 */
@Service
public class RequestContextHolderWrapper {

	public HttpSession getCurrentSession() {

		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		
		return attrs.getRequest().getSession(false);

	}
}
