package com.markitserv.msws.testutil;

import com.markitserv.msws.Type;

/**
 * Simple fake Type, used for testing
 * @author roy.truelove
 *
 */
public class FakeType extends Type {
	
	public FakeType(boolean successful) {
		super();
		this.setSuccessful(successful);
	}

	public boolean successful;

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
}
