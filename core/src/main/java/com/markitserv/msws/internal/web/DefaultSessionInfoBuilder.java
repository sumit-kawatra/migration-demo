package com.markitserv.msws.internal.web;

import com.markitserv.msws.beans.SessionInfo;
import com.markitserv.msws.web.AbstractSessionInfoBuilder;

/**
 * Default session info builder, which does not add any application-specific
 * information.
 * 
 * @author roy.truelove
 * 
 */
public class DefaultSessionInfoBuilder extends
		AbstractSessionInfoBuilder<SessionInfo> {

	@Override
	protected SessionInfo populateSessionInfo(SessionInfo originalSessionInfo) {
		return originalSessionInfo;
	}

	@Override
	protected SessionInfo createNewSessionInfo() {
		return new SessionInfo();
	}
}
