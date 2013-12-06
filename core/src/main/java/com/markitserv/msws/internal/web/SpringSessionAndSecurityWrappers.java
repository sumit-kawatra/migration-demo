package com.markitserv.msws.internal.web;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.markitserv.msws.internal.exceptions.ProgrammaticException;
import com.markitserv.msws.util.MswsAssert;

/**
 * Wraps Spring's RequestContextHolder and SecurityHolder, and provides helpers
 * around that class. The reason is that the class is static, and so is not
 * mockable. This should be the *only* way that the code retrieves request /
 * session level information. Asserts that you are in a session request thread
 * 
 * @author roy.truelove
 * 
 */
@Service
public class SpringSessionAndSecurityWrappers {

	public HttpSession getCurrentHttpSession() {

		ServletRequestAttributes attrs = this
				.getCurrentServletRequestAttributes();

		HttpSession session = attrs.getRequest().getSession(false);

		MswsAssert.mswsAssert(session != null,
				"Could not get session instance.  "
						+ "Are you sure you're within a web session thread?");

		return session;

	}

	/**
	 * Gets the request attributes from the current RequestContext, if there is
	 * one. If there isn't, an exception in thrown.
	 * 
	 * @return
	 */
	public ServletRequestAttributes getCurrentServletRequestAttributes() {

		ServletRequestAttributes attribs = null;

		// Throws an exception if we're not in a web request thread
		try {
			attribs = (ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes();
		} catch (Exception e) {
			throw new ProgrammaticException(
					"Could not get servlet request attributes.  "
							+ "Are you sure you're within a web session thread?");
		}

		return attribs;

	}

	public SecurityContext getSecurityContext() {
		return SecurityContextHolder.getContext();
	}

	// Convenience method that ensures you're in a session thread.
	public boolean isInSessionThread() {

		try {
			getCurrentServletRequestAttributes();
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	public void addRequestAttribute(String key, Object value) {

		ServletRequestAttributes attribs = this
				.getCurrentServletRequestAttributes();

		attribs.setAttribute(key, value, ServletRequestAttributes.SCOPE_REQUEST);

		RequestContextHolder.setRequestAttributes(attribs);

	}
}
