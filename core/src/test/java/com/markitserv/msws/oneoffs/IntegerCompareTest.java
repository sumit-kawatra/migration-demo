package com.markitserv.msws.oneoffs;

import org.junit.Test;

public class IntegerCompareTest {
	
	@Test
	public void compareInts() {
		Integer one = new Integer(1);
		Integer two = new Integer(2);
		
		System.out.println(one.compareTo(two));
		System.out.println(two.compareTo(one));
	}
}
