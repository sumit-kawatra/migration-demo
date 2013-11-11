package com.markitserv.msws.messaging.test;

import org.springframework.stereotype.Service;

@Service(value="commandHandlerMdb")
public class CommandHandlerMdb {

	public void handle(TestAsyncCommand e) {
		System.out.println("Got test async cmd");
	}
}
