/**
 * 
 */
package com.markitserv.msws.validation.small;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.markitserv.msws.validation.BooleanValidation;
import com.markitserv.msws.validation.ValidationResponse;

/**
 * @author kiran.gogula
 * 
 */
public class BooleanValidationTest {

	private BooleanValidation booleanValidation;

	@Before
	public void setUp() {
		booleanValidation = new BooleanValidation();
	}

	@After
	public void tearDown() {
		booleanValidation = null;
	}

	@Test
	public void testIsValidatedWhenInputIsTrue() {
		Assert.assertTrue(booleanValidation.validate(true, null).isValid());
	}

	@Test
	public void testIsValidatedWhenInputIsFalse() {
		Assert.assertTrue(booleanValidation.validate(false, null).isValid());
	}

	@Ignore
	@Test
	public void testIsValidatedWhenInputIsInValidString() {
		ValidationResponse res = booleanValidation.validate("yes", null);
		Assert.assertFalse(res.isValid());
		Assert.assertEquals("Expected Boolean", res.getMessage());
	}

	@Test
	public void testIsValidatedWhenInputIsaValidString() {
		ValidationResponse res = booleanValidation.validate("3", null);
		Assert.assertFalse(res.isValid());
	}

	@Test
	@Ignore
	public void testIsValidatedWhenInputIsaIntegerValue() {
		ValidationResponse res = booleanValidation.validate(1, null);
		Assert.assertFalse(res.isValid());
		Assert.assertEquals("Expected Boolean", res.getMessage());
	}

	@Test
	@Ignore
	public void testIsValidatedWhenInputIsaNullValue() {
		ValidationResponse res = booleanValidation.validate(null, null);
		Assert.assertEquals("Expected Boolean", res.getMessage());
	}

	@Test
	public void canConvertTrueStringToBoolean() {
		ValidationResponse res = booleanValidation.validate("true", null);
		Assert.assertTrue((Boolean) res.getConvertedObj());
	}

	@Test
	public void canConvertFalseStringToBoolean() {
		ValidationResponse res = booleanValidation.validate("false", null);
		Assert.assertFalse((Boolean) res.getConvertedObj());
	}

	@Test
	public void canNotConvertNotBooleanStringToBoolean() {
		ValidationResponse res = booleanValidation.validate("foo", null);
		Boolean convertedObj = (Boolean) res.getConvertedObj();
		Assert.assertNull(convertedObj);
		Assert.assertFalse(res.isValid());
	}
}
