package com.markitserv.msws.oneoffs.etc.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandlerA2 {
	
	Logger log = LoggerFactory.getLogger(CommandHandlerA2.class);
	
	public void onCommandA(CommandA cmd) {
		log.error("Got commandA A on CommandHandlerA2");
	}
}
