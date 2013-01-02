package com.markitserv.mwws.action;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.markitserv.mwws.exceptions.ProgrammaticException;
import com.markitserv.mwws.internal.UuidGenerator;

public abstract class Action implements InitializingBean {

	@Autowired
	private ActionRegistry actionRegistry;

	@Autowired
	private UuidGenerator uuidGenerator;

	public ActionResult performAction(ActionCommand command) {

		ActionResult result = this.performAction(command.getParameters(),
				command.getFilters());

		result.getMetadata().setRequestId(uuidGenerator.generateUuid());

		validateResult(result);

		return result;
	}

	/**
	 * Ensures that the result is valid before sending back to the client
	 * 
	 * @param result
	 */
	private void validateResult(ActionResult result) {

		String failureMsg = "Failed to validate ActionResult.  ";

		// either collection OR item needs to be set.
		if (result.getCollection() == null && result.getItem() == null) {
			throw new ProgrammaticException(failureMsg
					+ "Either the collection or the item must be set.");
		}
		
		// cannot have both collection and item set at the same time.  
		if (result.getCollection() != null && result.getItem() != null) {
			throw new ProgrammaticException(failureMsg
					+ "Cannot set both the collection and the item.");
		}
	}

	/**
	 * Where all the fun happens
	 * 
	 * @param params
	 * @param filters
	 * @return
	 */
	protected abstract ActionResult performAction(ActionParameters params,
			ActionFilters filters);

	private void registerWithActionRegistry() {
		actionRegistry.registerAction(this.getActionName(), this);
	}

	/*
	 * By default the name of the action that's put into the registry is the
	 * simple name of the class. Can be overriden if necessary (but when would
	 * it be necessary?)
	 */
	protected String getActionName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.registerWithActionRegistry();
	}
}
