package com.markitserv.msws.svc;

import org.springframework.stereotype.Service;

import com.markitserv.msws.internal.action.resp.HealthCheckResponse;
import com.markitserv.msws.internal.action.resp.HealthCheckResponse.Status;

@Service(value="healthChecker")
public class SimpleHealthChecker implements HealthChecker {

	@Override
	public HealthCheckResponse checkHealth() {
		
		HealthCheckResponse hcr = new HealthCheckResponse();
		hcr.setOverallHealth(Status.GOOD);
		
		return hcr;
	}
}
