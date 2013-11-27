package com.markitserv.msws.web;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.markitserv.msws.beans.SessionInfo;
import com.markitserv.msws.util.MswsAssert;
import com.markitserv.msws.util.SecurityAndSessionHelper;

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
	private SecurityAndSessionHelper sessionUtil;

	public T buildSessionInfo() {
		
		

		HttpSession session = sessionUtil.getSession();

		@SuppressWarnings("unchecked")
		T sInfo = (T) session
				.getAttribute(SecurityAndSessionHelper.SESSION_ATTRIB_SESSION_INFO);

		if (sInfo == null) {

			sInfo = this.createNewSessionInfo();

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

			// Add application-specific details
			sInfo = this.populateSessionInfo(sInfo);

			// Set it on the session in case it's needed later, but generally
			// users shoudl be popping it off of the ActionCommand
			session.setAttribute(
					SecurityAndSessionHelper.SESSION_ATTRIB_SESSION_INFO, sInfo);
		}

		return sInfo;

	}

	/**
	 * Populates the session info with application specific fields
	 * 
	 * @param originalSessionInfo
	 * @return
	 */
	protected abstract T populateSessionInfo(T originalSessionInfo);

	/**
	 * Creates an empty instance of SessionInfo, or more likely, a subclass of
	 * SessionInfo that has application specific fields.
	 * 
	 * @return
	 */
	protected abstract T createNewSessionInfo();

}
