package com.markitserv.msws.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.markitserv.msws.action.resp.ActionResult;
import com.markitserv.msws.internal.exceptions.LongPollingInterruptedException;

public abstract class AbstractLongPollingAction extends AbstractAction {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected final ActionResult performAction(ActionCommand cmd) {

		ActionResult result = null;

		try {
			result = this.performLongPollingAction(cmd);
		} catch (InterruptedException e) {
			log.warn("Long polling action was interrupted.  Client has been notified.");
			throw LongPollingInterruptedException.standardException();
		}

		return result;
	}

	protected abstract ActionResult performLongPollingAction(ActionCommand cmd)
			throws InterruptedException;

}
