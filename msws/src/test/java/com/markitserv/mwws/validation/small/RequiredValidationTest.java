package com.markitserv.mwws.validation.small;

import org.junit.Test;

import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.exceptions.ValidationException;
import com.markitserv.mwws.testutil.AbstractMswsTest;
import com.markitserv.mwws.validation.RequiredValidation;

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
