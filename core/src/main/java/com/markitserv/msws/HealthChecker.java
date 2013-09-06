package com.markitserv.msws;

import com.markitserv.msws.types.SuccessFailure;

public interface HealthChecker {
	public SuccessFailure checkHealth();
}
