package com.markitserv.mwws.Util;

public class Constants {

	/**
	 * Needed mostly for JSON serialization. Integers cannot be null, so Jackson
	 * will serialize 'empty' values.
	 */
	public final static int INTEGER_NOT_SET = Integer.MIN_VALUE;

}