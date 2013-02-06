/**
 * 
 */
package com.markitserv.msws.validation.small;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
	public void setUp(){
		booleanValidation = new BooleanValidation();
	}
	
	@After
	public void tearDown(){
		booleanValidation = null;
	}

	@Test
	public void testIsValidatedWhenInputIsTrue(){
		Assert.assertTrue(booleanValidation.isValid(true, null).isValid());
	}
	
	@Test
	public void testIsValidatedWhenInputIsFalse(){
		Assert.assertTrue(booleanValidation.isValid(false, null).isValid());
	}
	
	@Test
	public void testIsValidatedWhenInputIsInValidString(){
		ValidationResponse res  = booleanValidation.isValid("yes", null);
		Assert.assertFalse(res.isValid());
		Assert.assertEquals("Expected Boolean", res.getMessage());
	}
	
	@Test
	public void testIsValidatedWhenInputIsaValidString(){
		ValidationResponse res  = booleanValidation.isValid("3", null);
		Assert.assertFalse(res.isValid());
	}
	
	@Test
	public void testIsValidatedWhenInputIsaIntegerValue(){
		ValidationResponse res  = booleanValidation.isValid(1, null);
		Assert.assertFalse(res.isValid());
		Assert.assertEquals("Expected Boolean", res.getMessage());
	}
	
	@Test
	public void testIsValidatedWhenInputIsaNullValue() {
		ValidationResponse res = booleanValidation.isValid(null, null);
		Assert.assertEquals("Expected Boolean", res.getMessage());
	}

	
	
}
