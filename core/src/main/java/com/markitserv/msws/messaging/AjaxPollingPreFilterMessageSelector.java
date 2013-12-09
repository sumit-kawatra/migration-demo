package com.markitserv.msws.messaging;

import org.springframework.integration.Message;
import org.springframework.integration.core.MessageSelector;

import com.markitserv.msws.beans.AjaxPollingEvent;
import com.markitserv.msws.beans.MswsRequestContext;
import com.markitserv.msws.internal.request.MswsRequestContextHelper;

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

	private MswsRequestContextHelper mswsReqCtx;

	@Override
	public boolean accept(Message<?> message) {

		AjaxPollingEvent<?> payload = (AjaxPollingEvent<?>) message
				.getPayload();
		return this.acceptAjaxPollingEvent(payload);
	}

	/**
	 * This is necessary because when this code is being created it might not
	 * necessarily be in a thread that has session information (eg a scheduled
	 * thread).
	 * 
	 * @param ctx
	 */
	public void registerMswsRequestContext(MswsRequestContext ctx) {
		mswsReqCtx.registerRequestContextWithThread(ctx);
	}

	protected abstract boolean acceptAjaxPollingEvent(AjaxPollingEvent<?> event);
}
