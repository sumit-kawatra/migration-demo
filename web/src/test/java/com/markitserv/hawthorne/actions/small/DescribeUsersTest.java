package com.markitserv.hawthorne.actions.small;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.markitserv.hawthorne.actions.DescribeUsers;
import com.markitserv.hawthorne.util.HardcodedHawthorneBackend;
import com.markitserv.hawthorne.util.RandomNameGenerator;
import com.markitserv.msws.testutil.AbstractMswsTest;

public class DescribeUsersTest extends AbstractMswsTest {

	static HardcodedHawthorneBackend backend = new HardcodedHawthorneBackend();
	private DescribeUsers target;
	RandomNameGenerator nameGen;

	@Before
	public void setup() {
		nameGen = new RandomNameGenerator();
		try {
			nameGen.refresh(); // as we are using fresh object of nameGen
		} catch (IOException e) {
			System.out.println("Error while refreshing RandomNameGenerator() - "
					+ e.getMessage());
		}
		backend.setNameGen(nameGen);
		target = new DescribeUsers();
		target.setHawthorneBackend(backend);
	}

	@Test
	public void canRunWithParticipantId() {
		backend.populateAllHardcodedData();
		actionCommandBuilder.addParamCollectionElement("ParticipantId", 1);
		target.performAction(actionCommandBuilder.build());
	}
}
