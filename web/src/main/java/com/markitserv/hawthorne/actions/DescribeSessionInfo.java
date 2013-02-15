package com.markitserv.hawthorne.actions;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.markitserv.hawthorne.types.SessionInfo;
import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;

@Service
public class DescribeSessionInfo extends AbstractAction {

	Logger log = LoggerFactory.getLogger(DescribeSessionInfo.class);

	@Override
	protected ActionResult performAction(ActionParameters params, ActionFilters filters) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes()).getRequest().getSession();

		DateTime expiresOn = new DateTime(session.getCreationTime());
		int secondsTillExpire = session.getMaxInactiveInterval();

		expiresOn = expiresOn.plusSeconds(secondsTillExpire);

		SessionInfo sessionInfo = new SessionInfo();

		sessionInfo.setUsername(user.getUsername());
		List<String> roles = authoritiesToString(user.getAuthorities());
		sessionInfo.setRoles(roles);

		// following data is hardcoded until we get more info
		sessionInfo.setParticipantName("METRO_BANK");
		if (roles.get(0).equals("ROLE_USER"))
			sessionInfo.setFullName("Sally User");
		else if (roles.get(0).equals("ROLE_ADMIN"))
			sessionInfo.setFullName("Sam Admin");

		return new ActionResult(sessionInfo);
	}

	private List<String> authoritiesToString(Collection<GrantedAuthority> authorities) {
		Stack<String> roles = new Stack<String>();

		for (GrantedAuthority auth : authorities) {
			roles.push(auth.getAuthority());
		}

		return roles;
	}
}