package com.markitserv.hawthorne.actions.small;

import org.junit.Before;
import org.junit.Test;

import com.markitserv.hawthorne.actions.DescribeUsers;
import com.markitserv.hawthorne.util.HardcodedHawthorneBackend;
import com.markitserv.msws.testutil.AbstractMswsTest;

public class DescribeUsersTest extends AbstractMswsTest {

	static HardcodedHawthorneBackend backend = new HardcodedHawthorneBackend();
	private DescribeUsers target;

	@Before
	public void setup() {
		target = new DescribeUsers();
		target.setHawthorneBackend(backend);
	}

	@Test
	public void canRunWithParticipantId() {

		actionCommandBuilder.addParamCollectionElement("ParticipantId", 1);
		target.performAction(actionCommandBuilder.build());
	}
}
