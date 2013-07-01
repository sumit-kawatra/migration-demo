/**
 * 
 */
package com.markitserv.msws.validation.small;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.markitserv.msws.validation.BooleanValidationAndConversion;
import com.markitserv.msws.validation.ValidationResponse;

/**
 * @author kiran.gogula
 *
 */
public class BooleanValidationTest {
	
	private BooleanValidationAndConversion booleanValidation;
	
	@Before
	public void setUp(){
		booleanValidation = new BooleanValidationAndConversion();
	}
	
	@After
	public void tearDown(){
		booleanValidation = null;
	}

	@Test
	public void testIsValidatedWhenInputIsTrue(){
		Assert.assertTrue(booleanValidation.validateAndConvert(true, null).isValid());
	}
	
	@Test
	public void testIsValidatedWhenInputIsFalse(){
		Assert.assertTrue(booleanValidation.validateAndConvert(false, null).isValid());
	}
	
	@Test
	public void testIsValidatedWhenInputIsInValidString(){
		ValidationResponse res  = booleanValidation.validateAndConvert("yes", null);
		Assert.assertFalse(res.isValid());
		Assert.assertEquals("Expected Boolean", res.getMessage());
	}
	
	@Test
	public void testIsValidatedWhenInputIsaValidString(){
		ValidationResponse res  = booleanValidation.validateAndConvert("3", null);
		Assert.assertFalse(res.isValid());
	}
	
	@Test
	public void testIsValidatedWhenInputIsaIntegerValue(){
		ValidationResponse res  = booleanValidation.validateAndConvert(1, null);
		Assert.assertFalse(res.isValid());
		Assert.assertEquals("Expected Boolean", res.getMessage());
	}
	
	@Test
	public void testIsValidatedWhenInputIsaNullValue() { 
		ValidationResponse res = booleanValidation.validateAndConvert(null, null);
		Assert.assertEquals("Expected Boolean", res.getMessage());
	}
	
	@Test
	public void canConvertTrueStringToBoolean() { 
		ValidationResponse res = booleanValidation.validateAndConvert("true", null);
		Assert.assertTrue((Boolean)res.getConvertedObj());
	}
	
	@Test
	public void canConvertFalseStringToBoolean() { 
		ValidationResponse res = booleanValidation.validateAndConvert("false", null);
		Assert.assertFalse((Boolean)res.getConvertedObj());
	}
	
	@Test
	public void canNotConvertNotBooleanStringToBoolean() { 
		ValidationResponse res = booleanValidation.validateAndConvert("foo", null);
		Boolean convertedObj = (Boolean)res.getConvertedObj();
		Assert.assertNull(convertedObj);
		Assert.assertFalse(res.isValid());
	}
}
