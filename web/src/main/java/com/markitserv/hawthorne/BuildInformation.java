package com.markitserv.hawthorne;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.markitserv.msws.exceptions.ProgrammaticException;

/**
 * Provides the git ID for this build
 * 
 * @author roy.truelove
 */
public class BuildInformation implements InitializingBean {

	private String buildPropertiesFileLocation = "git.properties";
	private String scmCommitIdPropName = "build.git.sha";

	private String build;

	private Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public void afterPropertiesSet() throws Exception {

		Properties props = this.loadProperties();

		build = props.getProperty(scmCommitIdPropName);
		log.info("Initializing code from build :" + build);
	}

	public Properties loadProperties() {

		Properties props = new Properties();

		PathMatchingResourcePatternResolver p = new PathMatchingResourcePatternResolver();

		try {
			props.load(p.getResource(buildPropertiesFileLocation)
					.getInputStream());
		} catch (IOException e) {
			throw new ProgrammaticException("Could not load git properties", e);
		}
		
		return props;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}
}
