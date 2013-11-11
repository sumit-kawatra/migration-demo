package com.markitserv.msws.messaging.test;

import org.springframework.stereotype.Service;

@Service(value="eventHandlerMdb")
public class EventHandlerMdb {

	public void handle(TestEvent e) {
		System.out.println("Got test event " + e.getFoo());
	}
}
