package com.markitserv.msws.util;

import com.markitserv.msws.exceptions.AssertionException;

/**
 * Used to ensure contract is met.  Throws AssertionException if not.
 * @author roy.truelove
 *
 */
public class MswsAssert {
	
	static public void mswsAssert(boolean exp) {
		mswsAssert(exp, null);
	}
	
	static public void mswsAssert(boolean exp, String message) {
		mswsAssert(exp, message, new Object[0]);
	}
	
	static public void mswsAssert(boolean exp, String message, Object... args) {
		
		String fullMsg = "Assertion failed";
		if (message != null) {
			fullMsg = fullMsg.concat(": ");
			fullMsg = fullMsg.concat(String.format(message, args));
		}
		
		if (!exp) {
			throw new AssertionException(fullMsg);
		}
	}
}
