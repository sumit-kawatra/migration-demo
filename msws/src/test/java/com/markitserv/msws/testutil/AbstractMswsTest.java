package com.markitserv.msws.testutil;

import org.junit.Before;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import org.mockito.Mockito;

import com.markitserv.msws.internal.UuidGenerator;
import com.markitserv.msws.testutil.ActionAndActionCommandHelpers.TestAction;
import com.markitserv.msws.testutil.ActionAndActionCommandHelpers.TestActionCommandBuilder;

/** 
 * Exposes some useful variables to the subclasses:
 * actionCommandBuilder - used to create a fake ActionCommand
 * testAction - a simple subclass of Action
 * 
 * commonly used mocks:
 * uuidGeneratorMock - mock of UuidGenerator
 * 
 * @author roy.truelove
 *
 */
public abstract class AbstractMswsTest {

	private ActionAndActionCommandHelpers helper;
	
	protected TestActionCommandBuilder actionCommandBuilder;
	protected TestAction fakeTestAction;

	// commonly used mocks
	protected UuidGenerator uuidGeneratorMock;

	@Before
	public void setupEach() {

		// Stubs
		uuidGeneratorMock = Mockito.mock(UuidGenerator.class, RETURNS_SMART_NULLS);
		Mockito.when(uuidGeneratorMock.generateUuid()).thenReturn("Stubbed UUID");

		helper = new ActionAndActionCommandHelpers();
		if (actionCommandBuilder == null) {
			actionCommandBuilder = helper.new TestActionCommandBuilder();
		}
		
		fakeTestAction = helper.new TestAction();

		fakeTestAction.setUuidGenerator(uuidGeneratorMock);
	}
}
