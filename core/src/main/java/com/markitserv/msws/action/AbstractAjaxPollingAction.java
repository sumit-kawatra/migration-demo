package com.markitserv.msws.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.markitserv.msws.action.resp.ActionResult;
import com.markitserv.msws.internal.exceptions.LongPollingInterruptedException;
import com.markitserv.msws.messaging.AjaxPollingQueue;

public abstract class AbstractAjaxPollingAction extends AbstractAction {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private AjaxPollingQueue queue;

	@Override
	protected final ActionResult performAction(ActionCommand cmd) {

		ActionResult result = null;

		try {
			result = this.performAjaxPollingAction(cmd);
		} catch (InterruptedException e) {
			log.warn("Long polling action was interrupted.  Client has been notified.");
			throw LongPollingInterruptedException.standardException();
		}

		return result;
	}

	protected abstract ActionResult performAjaxPollingAction(ActionCommand cmd)
			throws InterruptedException;

	public AjaxPollingQueue getAjaxPollingQueue() {
		return queue;
	}

	@Required
	public void setAjaxPollingQueue(AjaxPollingQueue queue) {
		this.queue = queue;
	}
}
