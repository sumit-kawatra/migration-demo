package com.markitserv.msws.util;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.markitserv.msws.beans.SessionInfo;

/**
 * Wraps Session information. Since spring exposes a lot of this information
 * with static methods, this classes allows that information to be mocked. Can
 * only be used w/in a web request thread.
 * 
 * @author roy.truelove
 * 
 */

@Service
public class SessionHelper {

	public static final String SESSION_ATTRIB_SESSION_INFO = "SESSION_INFO";

	public User getUser() {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		if (auth != null) {
			return (User) auth.getPrincipal();
		}

		else
			return null;
	}

	/**
	 * Gets the session info, not casted to the application-specific version.
	 * Returns null if there is no SessionInfo yet on the session. Throws an
	 * error if there is no session
	 * 
	 * @return
	 */
	public SessionInfo getSessionInfoFromHttpSession() {

		return (SessionInfo) this.getHttpSession().getAttribute(
				SessionHelper.SESSION_ATTRIB_SESSION_INFO);
	}

	public void setSessionInfoToHttpSession(SessionInfo info) {

		this.getHttpSession().setAttribute(SESSION_ATTRIB_SESSION_INFO, info);
	}

	/**
	 * Gets the HTTP session from the thread.  If there is 
	 * @return
	 */
	public HttpSession getHttpSession() {

		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		MswsAssert.mswsAssert(servletRequestAttributes != null,
				"Could not get servlet request attributes.  "
						+ "Are you sure you're within a web session thread?");

		HttpSession session = servletRequestAttributes.getRequest().getSession(
				false);

		MswsAssert.mswsAssert(servletRequestAttributes != null,
				"Could not get session instance.  "
						+ "Are you sure you're within a web session thread?");

		return session;
	}
}
