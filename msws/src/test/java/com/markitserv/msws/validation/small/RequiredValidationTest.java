package com.markitserv.msws.validation.small;

import org.junit.Test;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.exceptions.ValidationException;
import com.markitserv.msws.testutil.AbstractMswsTest;
import com.markitserv.msws.validation.RequiredValidation;

public class RequiredValidationTest extends AbstractMswsTest {

	@Test
	public void succeedsIfRequiredParamProvided() {

		ActionCommand cmd = actionCommandBuilder.addParam("Required", "foo").build();
		fakeTestAction.addParameterValdiation("Required", new RequiredValidation());
		fakeTestAction.performAction(cmd);
	}

	@Test(expected = ValidationException.class)
	public void failsIfRequiredParamNotProvided() {
		
		fakeTestAction.addParameterValdiation("Required", new RequiredValidation());

		ActionCommand cmd = actionCommandBuilder.build();
		fakeTestAction.performAction(cmd);
	}
}
