package com.markitserv.msws.testutil;


/**
 * Simple fake Type, used for testing
 * @author roy.truelove
 *
 */
public class FakeType {
	
	public FakeType(boolean successful) {
		super();
		this.setSuccessful(successful);
	}

	public boolean successful;

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
}
