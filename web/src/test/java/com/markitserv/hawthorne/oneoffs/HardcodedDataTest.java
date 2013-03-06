package com.markitserv.hawthorne.oneoffs;

import org.junit.Test;

import com.markitserv.hawthorne.util.HardcodedHawthorneBackend;

public class HardcodedDataTest {

	HardcodedHawthorneBackend target = new HardcodedHawthorneBackend();

	@Test
	public void showParticipants() {
		System.out.println(target.buildAndGetParticipants());
	}
}
