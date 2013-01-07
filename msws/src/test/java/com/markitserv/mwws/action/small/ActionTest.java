package com.markitserv.mwws.action.small;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.internal.UuidGenerator;
import com.markitserv.mwws.testutil.ActionAndActionCommandHelpers;
import com.markitserv.mwws.testutil.ActionAndActionCommandHelpers.TestAction;
import com.markitserv.mwws.testutil.ActionAndActionCommandHelpers.TestActionCommandBuilder;
import com.markitserv.mwws.validation.OneOfValidation;
import com.markitserv.mwws.validation.RequiredValidation;

/**
 * Actually tests AbstractAction by making a simple subclass
 * @author roy.truelove
 *
 */
public class ActionTest {
	
	private static UuidGenerator uuidGenerator;
	private static ActionAndActionCommandHelpers helpers;
	private static TestActionCommandBuilder cmdBuilder;

	@BeforeClass
	public static void setupFixture() {
		
		// Stubs
		uuidGenerator = Mockito.mock(UuidGenerator.class);
		Mockito.when(uuidGenerator.generateUuid()).thenReturn("Stubbed UUID");

		helpers = new ActionAndActionCommandHelpers();
		cmdBuilder = helpers.new TestActionCommandBuilder();
	}

	private TestAction action;
	
	@Before
	public void setupEach() {
		action = helpers.new TestAction();
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
