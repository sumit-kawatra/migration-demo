package com.markitserv.hawthorne.types;

import com.markitserv.msws.Type;

/**
 * Wrapper around the build ID returned by the BuildInformation service
 * @author roy.truelove
 *
 */
public class Build extends Type {
	
	private String buildId;

	public Build(String buildId) {
		this.buildId = buildId;
	}

	public String getBuildId() {
		return buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

}
