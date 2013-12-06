package com.markitserv.msws.web;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.markitserv.msws.beans.SessionInfo;
import com.markitserv.msws.internal.web.SpringSessionAndSecurityWrappers;
import com.markitserv.msws.util.MswsAssert;

/**
 * Subclass this if you want to augment the session info with anything that's
 * specific to its implementation. For instance, adding the company id of the
 * user, etc. Need to register the instance in Spring with id
 * 'sessionInfoBuilder'
 * 
 * @author roy.truelove
 * 
 */
public abstract class AbstractSessionInfoBuilder {

	@Autowired
	private SpringSessionAndSecurityWrappers springReqCtxHolder;

	private static String SESSION_PARAM_SESSION_INFO = "SESSION_INFO";

	public SessionInfo buildSessionInfo() {

		SessionInfo sInfo = this.getExistingSessionInfoIfExists();

		if (sInfo != null) {
			return sInfo;
		}

		sInfo = new SessionInfo();

		User user = this.getUser();

		MswsAssert.mswsAssert(user != null, "User is null");

		String userName = user.getUsername();
		Collection<GrantedAuthority> authorities = user.getAuthorities();

		Set<String> roles = new HashSet<String>();

		for (GrantedAuthority grantedAuth : authorities) {
			roles.add(grantedAuth.getAuthority());
		}

		int ttl = springReqCtxHolder.getCurrentHttpSession()
				.getMaxInactiveInterval();

		sInfo.setSessionTtl(ttl);
		sInfo.setRoles(roles);
		sInfo.setUsername(userName);

		// Add application-specific details, if there are any.
		Map<String, Object> attribs = sInfo.getAttributes();
		attribs = this.addAttributes(attribs);
		sInfo.addAllAttributes(attribs);

		// register the SessionInfo with the HTTP session so that we only create
		// it once per session.
		this.springReqCtxHolder.getCurrentHttpSession().setAttribute(
				SESSION_PARAM_SESSION_INFO, sInfo);

		return sInfo;

	}

	private SessionInfo getExistingSessionInfoIfExists() {

		return (SessionInfo) springReqCtxHolder.getCurrentHttpSession()
				.getAttribute(SESSION_PARAM_SESSION_INFO);

	}

	public User getUser() {

		Authentication auth = springReqCtxHolder.getSecurityContext()
				.getAuthentication();

		if (auth != null) {
			return (User) auth.getPrincipal();
		}

		else
			return null;
	}

	/**
	 * Populates the session info with application-specific attributes.
	 * 
	 * @param originalSessionInfo
	 * @return
	 */
	protected abstract Map<String, Object> addAttributes(
			Map<String, Object> attribs);

}
