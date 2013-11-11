package com.markitserv.msws.action.small;

import org.junit.Before;
import org.junit.Test;

import com.markitserv.msws.action.internal.ActionCommand;
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
		target.internalPerformAction(cmd);
	}
	
	@Test(expected=AssertionException.class)
	public void failsIfTotalSizeNotSet() {
		ActionCommand cmd = actionCommandBuilder.addParam("Size", 10).build();
		target.dontSetTotalSize(true);
		target.internalPerformAction(cmd);
	}
}