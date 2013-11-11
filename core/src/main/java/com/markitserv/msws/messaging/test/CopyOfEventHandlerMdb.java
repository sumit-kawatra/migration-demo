package com.markitserv.msws.messaging.test;

import org.springframework.stereotype.Service;

@Service(value="eventHandlerMdb2")
public class CopyOfEventHandlerMdb {

	public void handle(TestEvent e) {
		System.out.println("Got test event 2" + e.getFoo());
	}
}
