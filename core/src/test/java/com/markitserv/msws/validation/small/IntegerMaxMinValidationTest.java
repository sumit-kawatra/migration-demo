package com.markitserv.msws.validation.small;

import static org.junit.Assert.*;

import org.junit.Test;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.exceptions.ValidationException;
import com.markitserv.msws.testutil.AbstractMswsTest;
import com.markitserv.msws.validation.IntegerMaxMinValidationAndConversion;
import com.markitserv.msws.validation.ValidationResponse;

public class IntegerMaxMinValidationTest extends AbstractMswsTest {

	@Test
	public void isValidatedWhenMinAndMaxValueIsUnlimited() {

		IntegerMaxMinValidationAndConversion v = new IntegerMaxMinValidationAndConversion(
				IntegerMaxMinValidationAndConversion.UNLIMITED, IntegerMaxMinValidationAndConversion.UNLIMITED);

		assertTrue(v.internalValidateAndConvert(10, null).isValid());
	}

	@Test
	public void isValidatedWhenValueIsGreaterThanMinValue() {

		IntegerMaxMinValidationAndConversion v = new IntegerMaxMinValidationAndConversion(1,
				IntegerMaxMinValidationAndConversion.UNLIMITED);

		assertTrue(v.internalValidateAndConvert(10, null).isValid());
	}

	@Test
	public void isValidatedWhenValueIsEqualsToMinValue() {

		IntegerMaxMinValidationAndConversion v = new IntegerMaxMinValidationAndConversion(10,
				IntegerMaxMinValidationAndConversion.UNLIMITED);

		assertTrue(v.internalValidateAndConvert(10, null).isValid());
	}

	@Test
	public void isValidatedWhenValueIsLessThanMaxValue() {

		IntegerMaxMinValidationAndConversion v = new IntegerMaxMinValidationAndConversion(
				IntegerMaxMinValidationAndConversion.UNLIMITED, 11);

		ValidationResponse resp = v.internalValidateAndConvert(10, null);
		assertNotNull(resp);
		assertTrue(resp.isValid());
	}

	@Test
	public void isValidatedWhenValueIsEqualsToMaxValue() {

		IntegerMaxMinValidationAndConversion v = new IntegerMaxMinValidationAndConversion(
				IntegerMaxMinValidationAndConversion.UNLIMITED, 10);

		assertTrue(v.internalValidateAndConvert(10, null).isValid());
	}

	@Test
	public void isValidatedWhenValueIsBetweenMaxAndMinValues() {

		IntegerMaxMinValidationAndConversion v = new IntegerMaxMinValidationAndConversion(1, 100);

		assertTrue(v.internalValidateAndConvert(10, null).isValid());
	}

	@Test
	public void isNotValidatedIfValueIsLessThanMinValue() {

		IntegerMaxMinValidationAndConversion v = new IntegerMaxMinValidationAndConversion(10,
				IntegerMaxMinValidationAndConversion.UNLIMITED);

		assertFalse(v.internalValidateAndConvert(9, null).isValid());
	}

	@Test
	public void isNotValidatedIfValueIsGreaterThanMaxValue() {

		IntegerMaxMinValidationAndConversion v = new IntegerMaxMinValidationAndConversion(
				IntegerMaxMinValidationAndConversion.UNLIMITED, 10);

		assertFalse(v.internalValidateAndConvert(11, null).isValid());
	}

	@Test
	public void isNotValidatedIfValueIsNotAnInteger() {

		IntegerMaxMinValidationAndConversion v = new IntegerMaxMinValidationAndConversion(
				IntegerMaxMinValidationAndConversion.UNLIMITED, 10);

		assertFalse(v.internalValidateAndConvert("foo", null).isValid());
	}
}
