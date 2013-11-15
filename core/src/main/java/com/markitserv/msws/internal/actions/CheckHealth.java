package com.markitserv.msws.internal.actions;

import java.util.HashMap;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.resp.ActionResult;
import com.markitserv.msws.internal.action.resp.HealthCheckResponse;
import com.markitserv.msws.internal.action.resp.HealthCheckResponse.Status;
import com.markitserv.msws.svc.HealthChecker;

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
	protected ActionResult performAction(ActionCommand cmd) {
		
		HealthCheckResponse resp = healthChecker.checkHealth();
		
		// Ensure that overall health is popualted correctly 
		HashMap<String, Status> allStatuses = resp.getModuleStatuses();
		Set<String> statuses = allStatuses.keySet();
		Status overallStatus = Status.GOOD;
		
		for (String status : statuses) {
			Status health = allStatuses.get(status);
			if (health.equals(Status.NOT_GOOD)) {
				overallStatus = Status.NOT_GOOD;
			}
		}
		
		resp.setOverallHealth(overallStatus);
				
		return new ActionResult(resp);
	}
}
