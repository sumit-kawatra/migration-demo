package com.markitserv.msws.types;

import java.util.HashMap;

public class HealthCheckResponse {
	
	public enum Status {
		GOOD, NOT_GOOD
	}
	
	private Status overallHealth;
	private HashMap<String, Status> statuses = new HashMap<String, Status>();
	private HashMap<String, Number> metrics = new HashMap<String, Number>();
	
	public void addStatus(String name, Status status) {
		statuses.put(name, status);
	}
	
	public void addMetric(String name, Number status) {
		metrics.put(name, status);
	}
	
	public HashMap<String, Status> getStatuses() {
		return statuses;
	}

	public HashMap<String, Number> getMetrics() {
		return metrics;
	}
	
	public Status getOverallHealth() {
		return overallHealth;
	}

	public void setOverallHealth(Status overallHealth) {
		this.overallHealth = overallHealth;
	}

}
