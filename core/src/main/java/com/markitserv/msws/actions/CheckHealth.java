package com.markitserv.msws.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.HealthChecker;
import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;

/**
 * Checks the health of the system. Requires an instance of HealthChecker in the
 * spring config
 * 
 * @author roy.truelove
 * 
 */
@Service
public class CheckHealth extends AbstractAction {

	@Autowired
	private HealthChecker healthChecker;

	@Override
	protected ActionResult performAction(ActionParameters params,
			ActionFilters filters) {
		return new ActionResult(healthChecker.checkHealth());
	}
}
