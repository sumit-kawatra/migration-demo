package com.markitserv.msws.util;

import java.util.Set;

import com.markitserv.msws.beans.SessionInfo;

/**
 * Wraps security access information
 * 
 * @author roy.truelove
 * 
 */
public class SecurityHelper {

	public boolean isUserInRole(SessionInfo sessionInfo, String role) {

		MswsAssert.mswsAssert(sessionInfo != null && role != null);

		Set<String> userRoles = sessionInfo.getUser().getRoles();

		for (String userRole : userRoles) {

			if (userRole.equals(role)) {
				return true;
			}
		}

		return false;
	}

}
