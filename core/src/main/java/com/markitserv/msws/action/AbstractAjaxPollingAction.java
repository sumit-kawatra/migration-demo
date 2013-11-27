package com.markitserv.msws.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.markitserv.msws.action.resp.ActionResult;
import com.markitserv.msws.beans.AjaxPollingEvent;
import com.markitserv.msws.internal.exceptions.LongPollingInterruptedException;
import com.markitserv.msws.internal.filters.AjaxEventTypeFilter;
import com.markitserv.msws.messaging.AjaxPollingQueue;
import com.markitserv.msws.validation.BooleanValidation;
import com.markitserv.msws.validation.ListValidation;

public abstract class AbstractAjaxPollingAction extends AbstractAction {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String PARAM_BLOCKS = "Blocks";
	private static final String PARAM_EVENT_TYPE = "EventType";

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

	/**
	 * Adds 'blocks', so that a client can say if they're blocking or not.
	 */
	@Override
	protected ParamsAndFiltersDefinition addAdditionalParameterDefinitions(
			ParamsAndFiltersDefinition def) {

		def = def.addValidation(PARAM_BLOCKS, new BooleanValidation())
				.addDefaultParamValue(PARAM_BLOCKS, "true")
				.addValidation(PARAM_EVENT_TYPE, new ListValidation());

		return def;

	}

	/**
	 * Called by the subclass to the data.
	 * 
	 * @param cmd
	 * @return
	 */
	protected List<AjaxPollingEvent<?>> receive(ActionCommand cmd) {

		ActionParameters params = cmd.getParameters();
		Boolean blocks = params.getParameter(PARAM_BLOCKS, Boolean.class);

		List<AjaxPollingEvent<?>> msgs;
		msgs = this.queue.receive(blocks);

		// filter out unwanted event types
		if (params.isParameterSet(PARAM_EVENT_TYPE)) {

			@SuppressWarnings("unchecked")
			List<String> validEventTypes = params.getParameter(
					PARAM_EVENT_TYPE, List.class);

			msgs = AjaxEventTypeFilter.filter(msgs, validEventTypes);
		}

		return msgs;

	}

	protected abstract ActionResult performAjaxPollingAction(ActionCommand cmd)
			throws InterruptedException;

	public AjaxPollingQueue getAjaxPollingQueue() {
		return queue;
	}

	@Required
	@Autowired
	// should it be? Can it be overriden if needed? Need to test
	public void setAjaxPollingQueue(AjaxPollingQueue queue) {
		this.queue = queue;
	}
}
