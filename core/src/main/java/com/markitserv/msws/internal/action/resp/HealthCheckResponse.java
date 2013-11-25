package com.markitserv.msws.internal.action.resp;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HealthCheckResponse {
	
	public enum Status {
		GOOD, NOT_GOOD
	}
	
	private Status overallHealth;
	private HashMap<String, Status> statuses = new HashMap<String, Status>();
	private HashMap<String, Number> metrics = new HashMap<String, Number>();
	
	public void addModuleStatus(String moduleName, Status status) {
		statuses.put(moduleName, status);
	}
	
	public void addMetric(String name, Number status) {
		metrics.put(name, status);
	}
	
	@JsonProperty("modules")
	public HashMap<String, Status> getModuleStatuses() {
		return statuses;
	}

	public HashMap<String, Number> getMetrics() {
		return metrics;
	}
	
	@JsonProperty("system")
	public Status getOverallHealth() {
		return overallHealth;
	}

	public void setOverallHealth(Status overallHealth) {
		this.overallHealth = overallHealth;
	}

}
