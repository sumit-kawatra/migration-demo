package com.markitserv.mwws.internal;

import com.markitserv.mwws.exceptions.AssertionException;

/**
 * Used to ensure contract is met.  Throws AssertionException if not.
 * @author roy.truelove
 *
 */
public class MwwsAssert {
	
	static public void mwwsAssert(boolean exp) {
		mwwsAssert(exp, null);
	}
	
	static public void mwwsAssert(boolean exp, String message) {
		mwwsAssert(exp, message, new Object[0]);
	}
	
	static public void mwwsAssert(boolean exp, String message, Object... args) {
		
		String fullMsg = "Assertion failed";
		if (message != null) {
			fullMsg.concat(": ");
			fullMsg.concat(String.format(message, args));
		}
		
		if (!exp) {
			throw new AssertionException(fullMsg);
		}
	}
}
