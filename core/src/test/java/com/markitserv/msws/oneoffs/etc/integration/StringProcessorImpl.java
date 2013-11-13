package com.markitserv.msws.oneoffs.etc.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringProcessorImpl {
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	public String processInt(int x) {
		log.error("Got int " + x + ".  Returning");
		return "Got int " + x;
	}
} 