package com.markitserv.msws.messaging;

import org.springframework.integration.Message;
import org.springframework.integration.core.MessageSelector;

/**
 * Selector that's associated with a given AjaxPollingQueue.
 * 
 * @author roy.truelove
 * 
 */
public class AbstractAjaxPollingMessageSelector implements MessageSelector {

	@Override
	public boolean accept(Message<?> message) {
		// TODO Auto-generated method stub
		return false;
	}

}
