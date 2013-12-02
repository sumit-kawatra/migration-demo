package com.markitserv.msws.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.core.MessageSelector;

import com.markitserv.msws.beans.AjaxPollingEvent;
import com.markitserv.msws.util.SessionHelper;

/**
 * Pre filter that can be used to ensure that some messages don't even make it
 * on to the AjaxPollingQueue for a particular session. Useful for filtering
 * messages that might not be applicable for a given company, or for a user with
 * certain security credentials. Expects to be in the session scope, and so will
 * likely use some data from the SecurityAndSessionHelper.getSessionInfo() to
 * make its determination.
 * 
 * @author roy.truelove
 * 
 */
public abstract class AjaxPollingPreFilterMessageSelector implements
		MessageSelector {

	@Autowired
	SessionHelper helper;

	@Override
	public boolean accept(Message<?> message) {

		AjaxPollingEvent<?> payload = (AjaxPollingEvent<?>) message
				.getPayload();
		return this.acceptAjaxPollingEvent(payload);
	}

	public abstract boolean acceptAjaxPollingEvent(AjaxPollingEvent<?> event);
}
