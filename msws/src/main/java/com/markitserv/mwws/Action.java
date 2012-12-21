package com.markitserv.mwws;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Action implements InitializingBean {
	
	@Autowired
	private ActionRegistry actionRegistry;
	
	public abstract ActionResult performAction(ActionCommand command);
	
	private void registerWithActionRegistry() {
		actionRegistry.registerAction(this.getActionName(), this);
	}
	
	/*
	 * By default the name of the action that's put into the registry is the simple
	 * name of the class.  Can be overriden if necessary (but when would it be necessisary?)
	 */
	protected String getActionName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.registerWithActionRegistry();
	}
}
