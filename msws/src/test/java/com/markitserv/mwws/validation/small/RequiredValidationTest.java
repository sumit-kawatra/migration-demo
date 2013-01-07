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
		testAction.addParameterValdiation("Required", new RequiredValidation());
		testAction.performAction(cmd);
	}

	@Test(expected = ValidationException.class)
	public void failsIfRequiredParamNotProvided() {
		
		testAction.addParameterValdiation("Required", new RequiredValidation());

		ActionCommand cmd = actionCommandBuilder.build();
		testAction.performAction(cmd);
	}
}
