package com.markitserv.msws.oneoffs.etc.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandlerB1 {
	
	Logger log = LoggerFactory.getLogger(CommandHandlerB1.class);
	
	public String onCommandB(CommandB cmd) {
		log.error("Got commandB on CommandHandlerB1.  Returning 'foo'");
		return "foo";
	}
}
