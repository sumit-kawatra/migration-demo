package com.markitserv.msws.action.small;

import org.junit.Before;
import org.junit.Test;
import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.testutil.AbstractMswsTest;
import com.markitserv.msws.testutil.FakePaginatedAction;
import com.markitserv.msws.exceptions.AssertionException;

public class PaginatedActionTest extends AbstractMswsTest {
	
	FakePaginatedAction target;
	
	@Before
	public void setup() {
		target = new FakePaginatedAction();
	}
	
	@Test
	public void canRunHappyPath() {
		ActionCommand cmd = actionCommandBuilder.addParam("Size", 10).build();
		target.performAction(cmd);
	}
	
	@Test(expected=AssertionException.class)
	public void failsIfEitherTotalSizeNotSet() {
		ActionCommand cmd = actionCommandBuilder.addParam("Size", 10).build();
		target.dontSetTotalSize(true);
		target.performAction(cmd);
	}
}