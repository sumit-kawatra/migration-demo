package com.markitserv.msws.internal;

public class Constants {

	/**
	 * Needed mostly for JSON serialization. Integers cannot be null, so Jackson
	 * will serialize 'empty' values.
	 */
	public final static int INTEGER_NOT_SET = Integer.MIN_VALUE;
	
	public static final String UUID = "UUID";

}
