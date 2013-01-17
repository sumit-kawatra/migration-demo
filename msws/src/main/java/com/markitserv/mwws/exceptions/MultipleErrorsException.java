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
	
	public MultipleErrorsException() {
		super();
	}
	public MultipleErrorsException(String msg) {
		super(msg);
	}
	
	public MultipleErrorsException(String msg, List<String> allMsgs) {
		super(msg);
		this.messages = allMsgs;
	}

}
