package com.markitserv.mwws.action.small;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.markitserv.mwws.action.AbstractAction;
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
		
		AbstractAction expected = Mockito.mock(AbstractAction.class);
		target.registerAction("someAction", expected);
		
		AbstractAction actual = target.getActionWithName("someAction");
		
		assertEquals(expected, actual);
	}

	@Test(expected=UnknownActionException.class)
	public void missingActionFails() {
		AbstractAction actual = target.getActionWithName("someAction");
	}
} 