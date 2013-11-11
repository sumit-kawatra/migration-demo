package com.markitserv.msws.util;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.markitserv.msws.internal.MswsAssert;

/**
 * Wraps Security and Session information. Since spring exposes a lot of this
 * information with static methods, this classes allows that information to be
 * mocked.
 * 
 * @author roy.truelove
 * 
 */

@Service
public class SecurityAndSessionUtil {

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public User getUser() {
		Authentication auth = this.getAuthentication();
		if (auth != null) {
			return (User) auth.getPrincipal();
		}

		else
			return null;
	}

	public boolean isUserInRole(String role) {

		if (this.getUser() == null) {
			return false;
		}

		Collection<GrantedAuthority> usersRoles = this.getUser()
				.getAuthorities();

		for (GrantedAuthority auth : usersRoles) {

			if (auth.equals(role)) {
				return true;
			}
		}

		return false;
	}

	public HttpSession getSession() {

		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		MswsAssert.mswsAssert(servletRequestAttributes != null,
				"Could not get servlet request attributes.  "
						+ "Are you sure you're within a web session?");

		HttpSession session = servletRequestAttributes.getRequest().getSession(
				false);

		MswsAssert.mswsAssert(servletRequestAttributes != null,
				"Could not get session instance.  "
						+ "Are you sure you're within a web session?");

		return session;
	}
}
