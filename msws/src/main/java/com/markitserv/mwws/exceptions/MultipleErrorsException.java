package com.markitserv.mwws.exceptions;

import java.util.List;

public class MultipleErrorsException extends MwwsException {
	
	private List<String> messages;

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
