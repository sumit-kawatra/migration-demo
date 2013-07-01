/**
 * 
 */
package com.markitserv.msws.validation.small;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.markitserv.msws.validation.IntegerValidation;
import com.markitserv.msws.validation.ValidationResponse;

/**
 * @author kiran.gogula
 *
 */
public class IntegerValidationTest {
	
	private IntegerValidation integerValidation;
	private ValidationResponse validationResponse;
	
	@Before
	public void setUp(){
		integerValidation = new IntegerValidation();
	}
	
	@After
	public void tearDown(){
		validationResponse = null;
		integerValidation = null;
	}
	
	@Test
	public void testIsValidMethodWithIntergerValue(){
		validationResponse = integerValidation.validateInternal(1, null);
		Assert.assertEquals(true, validationResponse.isValid());
	}
	
	@Test
	public void testIsValidMethodWithIntergerValueInStringFormat(){
		validationResponse = integerValidation.validateInternal("1", null);
		Assert.assertEquals(true, validationResponse.isValid());
	}
	
	@Test
	public void testIsValidMethodWithIntergerValueInStringFormatButWhichIsNotInt(){
		validationResponse = integerValidation.validateInternal("true", null);
		Assert.assertEquals(false, validationResponse.isValid());
	}
	
	@Test
	public void testIsValidMethodWithBooleanValueExpectedExcetpion(){
		validationResponse = integerValidation.validateInternal(true, null);
		Assert.assertEquals(false, validationResponse.isValid());
	}
	
	@Test
	public void canConvertStringIntToInt(){
		validationResponse = integerValidation.validateInternal("1", null);
		Assert.assertEquals(1, validationResponse.getConvertedObj());
	}
	
	@Test
	public void cantConvertNonIntStringIntToInt(){
		validationResponse = integerValidation.validateInternal("1x", null);
		Assert.assertNull(validationResponse.getConvertedObj());
		Assert.assertFalse(validationResponse.isValid());
	}
}
