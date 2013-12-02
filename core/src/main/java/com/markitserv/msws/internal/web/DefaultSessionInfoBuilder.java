package com.markitserv.msws.internal.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected SessionInfo postPopulateSessionInfo(
			SessionInfo originalSessionInfo) {

		return originalSessionInfo;
	}

	@Override
	protected SessionInfo createSessionInfoInstance() {

		log.info("Using DefaultSessionInfoBuilder, which "
				+ "does not augment session info with any application-specific information.");

		return new SessionInfo();
	}

}