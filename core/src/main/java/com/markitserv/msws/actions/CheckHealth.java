package com.markitserv.msws.actions;

import java.util.HashMap;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.HealthChecker;
import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.types.HealthCheckResponse;
import com.markitserv.msws.types.HealthCheckResponse.Status;

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
