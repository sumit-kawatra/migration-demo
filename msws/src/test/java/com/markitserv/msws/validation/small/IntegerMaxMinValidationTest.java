package com.markitserv.msws.validation.small;

import org.junit.Test;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.exceptions.ValidationException;
import com.markitserv.msws.testutil.AbstractMswsTest;
import com.markitserv.msws.validation.IntegerMaxMinValidation;

public class IntegerMaxMinValidationTest extends AbstractMswsTest {

	@Test
	public void isValidatedWhenMinAndMaxValueIsUnlimited() {

		ActionCommand cmd = actionCommandBuilder.addParam("Value", 10).build();

		fakeTestAction.addParameterValdiation("Value", new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED,
				IntegerMaxMinValidation.UNLIMITED));
		fakeTestAction.performAction(cmd);
	}

	@Test
	public void isValidatedWhenValueIsGreaterThanMinValue() {

		ActionCommand cmd = actionCommandBuilder.addParam("Value", 10).build();

		fakeTestAction.addParameterValdiation("Value", new IntegerMaxMinValidation(1,
				IntegerMaxMinValidation.UNLIMITED));
		fakeTestAction.performAction(cmd);
	}

	@Test
	public void isValidatedWhenValueIsEqualsToMinValue() {

		ActionCommand cmd = actionCommandBuilder.addParam("Value", 10).build();

		fakeTestAction.addParameterValdiation("Value", new IntegerMaxMinValidation(10,
				IntegerMaxMinValidation.UNLIMITED));
		fakeTestAction.performAction(cmd);

	}

	@Test
	public void isValidatedWhenValueIsLessThanMaxValue() {

		ActionCommand cmd = actionCommandBuilder.addParam("Value", 10).build();

		fakeTestAction.addParameterValdiation("Value", new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 11));
		fakeTestAction.performAction(cmd);

	}

	@Test
	public void isValidatedWhenValueIsEqualsToMaxValue() {
		
		ActionCommand cmd = actionCommandBuilder.addParam("Value", 10).build();

		fakeTestAction.addParameterValdiation("Value", new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 10));
		fakeTestAction.performAction(cmd);
	}
	
	@Test
	public void isValidatedWhenValueIsBetweenMaxAndMinValues() {
		
		ActionCommand cmd = actionCommandBuilder.addParam("Value", 10).build();

		fakeTestAction.addParameterValdiation("Value", new IntegerMaxMinValidation(
				1, 100));
		fakeTestAction.performAction(cmd);
	}

	@Test(expected = ValidationException.class)
	public void isNotValidatedIfValueIsLessThanMinValue() {

		ActionCommand cmd = actionCommandBuilder.addParam("Value", 9).build();

		fakeTestAction.addParameterValdiation("Value", new IntegerMaxMinValidation(10,
				IntegerMaxMinValidation.UNLIMITED));
		fakeTestAction.performAction(cmd);
	}

	@Test(expected = ValidationException.class)
	public void isNotValidatedIfValueIsGreaterThanMaxValue() {
		
		ActionCommand cmd = actionCommandBuilder.addParam("Value", 11).build();

		fakeTestAction.addParameterValdiation("Value", new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 10));
		fakeTestAction.performAction(cmd);
	}

	@Test(expected = ValidationException.class)
	public void isNotValidatedIfValueIsNotAnInteger() {

		ActionCommand cmd = actionCommandBuilder.addParam("Value", "foo").build();

		fakeTestAction.addParameterValdiation("Value", new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 10));
		fakeTestAction.performAction(cmd);
	}
}
