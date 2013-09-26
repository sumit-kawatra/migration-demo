package com.markitserv.msws.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.markitserv.msws.exceptions.LongPollingInterruptedException;

public abstract class AbstractLongPollingAction extends AbstractAction {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected final ActionResult performAction(ActionParameters params,
			ActionFilters filters) {

		ActionResult result = null;
		
		try {
			result = this.performLongPollingAction(params, filters);
		} catch (InterruptedException e) {
			log.warn("Long polling action was interrupted.  Client has been notified.");
			throw LongPollingInterruptedException.standardException();
		}
		
		return result;
	}

	protected abstract ActionResult performLongPollingAction(
			ActionParameters params, ActionFilters filters)
			throws InterruptedException;
}
