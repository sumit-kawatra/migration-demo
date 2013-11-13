package com.markitserv.msws.oneoffs.etc.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventHandlerA1 {
	
	Logger log = LoggerFactory.getLogger(EventHandlerA1.class);
	
	public void onEventA(EventA event) {
		
		log.error("Got event A on eventHanlder A");
		
	}
}
