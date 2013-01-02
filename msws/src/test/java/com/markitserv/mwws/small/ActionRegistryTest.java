package com.markitserv.mwws.small;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.markitserv.mwws.action.Action;
import com.markitserv.mwws.action.ActionRegistry;
import com.markitserv.mwws.exceptions.UnknownActionException;

public class ActionRegistryTest {

	ActionRegistry target = new ActionRegistry();

	@Before
	public void setupEach() {
		target = new ActionRegistry();
	}

	@Test
	public void canAddActionToRegistry() {
		
		Action expected = Mockito.mock(Action.class);
		target.registerAction("someAction", expected);
		
		Action actual = target.getActionWithName("someAction");
		
		assertEquals(expected, actual);
	}

	@Test(expected=UnknownActionException.class)
	public void missingActionFails() {
		Action actual = target.getActionWithName("someAction");
	}
} 