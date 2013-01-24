package com.markitserv.mwws.validation.small;

import org.junit.Test;

import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.exceptions.ValidationException;
import com.markitserv.mwws.testutil.AbstractMswsTest;
import com.markitserv.mwws.validation.OneOfValidation;
import com.markitserv.mwws.validation.RequiredValidation;

public class OneOfValidationTest extends AbstractMswsTest {

	@Test
	public void isValidatedIfOneOfValuesIsProvided() {

		ActionCommand cmd = actionCommandBuilder.addParam("Value", "foo").build();
		
		String[] x = {"bar", "foo", "baz"};
		
		fakeTestAction.addParameterValdiation("Value", new OneOfValidation(x));
		fakeTestAction.performAction(cmd);
	}

	@Test(expected = ValidationException.class)
	public void isNotValidatedIfValueIsNotInListOfOneOfValues() {
		
		ActionCommand cmd = actionCommandBuilder.addParam("Value", "boo").build();
		
		String[] x = {"bar", "foo", "baz"};
		
		fakeTestAction.addParameterValdiation("Value", new OneOfValidation(x));
		fakeTestAction.performAction(cmd);
	}
}
