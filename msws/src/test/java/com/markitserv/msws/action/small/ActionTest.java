package com.markitserv.msws.action.small;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.internal.UuidGenerator;
import com.markitserv.msws.testutil.FakeAction;
import com.markitserv.msws.testutil.TestActionCommandBuilder;
import com.markitserv.msws.validation.OneOfValidation;
import com.markitserv.msws.validation.RequiredValidation;

/**
 * Actually tests AbstractAction by making a simple subclass
 * @author roy.truelove
 *
 */
public class ActionTest {
	
	private static UuidGenerator uuidGenerator;
	private static TestActionCommandBuilder cmdBuilder;

	@BeforeClass
	public static void setupFixture() {
		
		// Stubs
		uuidGenerator = Mockito.mock(UuidGenerator.class);
		Mockito.when(uuidGenerator.generateUuid()).thenReturn("Stubbed UUID");

		cmdBuilder = new TestActionCommandBuilder();
	}

	private FakeAction action;
	
	@Before
	public void setupEach() {
		action = new FakeAction();
		action.setUuidGenerator(uuidGenerator);
	}

	@Test
	public void itCanSetDefaultParamsOnAction() {
		
		action.addParameterDefault("Defaulted", "foo");
		ActionCommand cmd = cmdBuilder.build(); // has no parameters

		// require that 'Defaulted' is there even though we didn't add it to the
		// cmd.
		action.addParameterValdiation("Defaulted", new RequiredValidation());

		action.performAction(cmd);
	}
	
	@Test
	public void itWillNotOverrideValuesProvidedExplictlyByTheRequest() {
		
		action.addParameterDefault("Defaulted", "foo");
		ActionCommand cmd = cmdBuilder.addParam("Defaulted", "bar").build();

		String[] validValue = {"bar"};
		action.addParameterValdiation("Defaulted", new RequiredValidation());
		action.addParameterValdiation("Defaulted", new OneOfValidation(validValue));

		action.performAction(cmd);
	}
} 
