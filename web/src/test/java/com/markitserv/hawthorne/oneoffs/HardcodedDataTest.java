package com.markitserv.hawthorne.oneoffs;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.markitserv.hawthorne.util.HardcodedHawthorneBackend;
import com.markitserv.hawthorne.util.RandomNameGenerator;

public class HardcodedDataTest {

	HardcodedHawthorneBackend target;
	RandomNameGenerator nameGen;

	@Before
	public void init() {
		target = new HardcodedHawthorneBackend();
		nameGen = new RandomNameGenerator();
		try {
			nameGen.refresh(); // as we are using fresh object of nameGen
		} catch (IOException e) {
			System.out.println("Error while refreshing RandomNameGenerator() - "
					+ e.getMessage());
		}
		target.setNameGen(nameGen);
	}

	@Test
	public void showParticipants() {
		System.out.println(target.populateAllHardcodedData());
	}
}
