package com.markitserv.msws.security.web;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.core.Authentication;

public class MswsAuthenticatedVoter extends AuthenticatedVoter {

	@Override
	public int vote(Authentication authentication, Object object,
			Collection<ConfigAttribute> attributes) {

		int vote = super.vote(authentication, object, attributes);
		return vote;
	}

}
