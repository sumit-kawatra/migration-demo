package com.markitserv.msws.web;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.markitserv.msws.beans.SessionInfo;
import com.markitserv.msws.internal.exceptions.ProgrammaticException;
import com.markitserv.msws.util.MswsAssert;
import com.markitserv.msws.util.SessionHelper;

/**
 * Used if a client wants to augment the session info with anything that's
 * specific to its implementation. For instance, adding the company id of the
 * user, etc.
 * 
 * @author roy.truelove
 * 
 */
public abstract class AbstractSessionInfoBuilder<T extends SessionInfo> {

	@Autowired
	private SessionHelper sessionUtil;

	@SuppressWarnings("unchecked")
	public T buildAndPopulateSessionInfo() {

		HttpSession session = sessionUtil.getHttpSession();

		T sInfo = (T) sessionUtil.getSessionInfoFromHttpSession();

		// null means it's not yet on the session. Need to create
		if (sInfo == null) {

			sInfo = (T) this.createSessionInfoInstance();

			User user = (User) sessionUtil.getUser();

			MswsAssert.mswsAssert(user != null, "User is null");

			String userName = user.getUsername();
			Collection<GrantedAuthority> authorities = user.getAuthorities();

			Set<String> roles = new HashSet<String>();

			for (GrantedAuthority grantedAuth : authorities) {
				roles.add(grantedAuth.getAuthority());
			}

			int ttl = session.getMaxInactiveInterval();

			sInfo.setTtl(ttl);
			sInfo.setRoles(roles);
			sInfo.setUsername(userName);

			// Add application-specific details, if there are any.
			this.postPopulateSessionInfo((T) sInfo);

			// Set it on the session in case it's needed later, but generally
			// users shoudl be popping it off of the ActionCommand

			sessionUtil.setSessionInfoToHttpSession(sInfo);
		}

		return sInfo;

	}

	/**
	 * Overriden by subclass to create an instance of the application-specific
	 * SessionInfo.
	 * 
	 * @return
	 */
	protected abstract SessionInfo createSessionInfoInstance();

	/**
	 * Populates the session info with application specific fields, after it's
	 * already been populated with the standards
	 * 
	 * @param originalSessionInfo
	 * @return
	 */
	protected abstract T postPopulateSessionInfo(T originalSessionInfo);

}
