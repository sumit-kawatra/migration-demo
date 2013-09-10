package com.markitserv.msws;

import com.markitserv.msws.types.HealthCheckResponse;

public interface HealthChecker {
	public HealthCheckResponse checkHealth();
}
