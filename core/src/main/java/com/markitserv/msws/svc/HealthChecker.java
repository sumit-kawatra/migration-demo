package com.markitserv.msws.svc;

import com.markitserv.msws.internal.action.resp.HealthCheckResponse;

public interface HealthChecker {
	public HealthCheckResponse checkHealth();
}
