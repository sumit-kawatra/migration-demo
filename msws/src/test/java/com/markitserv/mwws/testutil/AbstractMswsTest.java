package com.markitserv.mwws.testutil;

import org.junit.Before;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import org.mockito.Mockito;

import com.markitserv.mwws.internal.UuidGenerator;
import com.markitserv.mwws.testutil.ActionAndActionCommandHelpers.TestAction;
import com.markitserv.mwws.testutil.ActionAndActionCommandHelpers.TestActionCommandBuilder;

/** 
 * Exposes some useful parameters:
 * actionCommandBuilder - builds and ActionCommand that can be performed
 * TestAction - a fake action that's running the built ActionCommand
 * @author roy.truelove
 *
 */
public abstract class AbstractMswsTest {

	private ActionAndActionCommandHelpers helper;
	
	protected TestActionCommandBuilder actionCommandBuilder;
	protected TestAction testAction;

	// dependencies
	protected UuidGenerator uuidGenerator;

	@Before
	public void setupEach() {

		// Stubs
		uuidGenerator = Mockito.mock(UuidGenerator.class, RETURNS_SMART_NULLS);
		Mockito.when(uuidGenerator.generateUuid()).thenReturn("Stubbed UUID");

		helper = new ActionAndActionCommandHelpers();
		if (actionCommandBuilder == null) {
			actionCommandBuilder = helper.new TestActionCommandBuilder();
		}
		
		testAction = helper.new TestAction();

		testAction.setUuidGenerator(uuidGenerator);
	}
}
