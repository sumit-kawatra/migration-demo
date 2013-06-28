/**
 * 
 */
package com.markitserv.msws.validation.small;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.markitserv.msws.validation.PaginationPageSizeValidation;
import com.markitserv.msws.validation.ValidationAndConversionResponse;

/**
 * @author kiran.gogula
 *
 */
public class PaginationPageSizeValidationTest {
	
	private PaginationPageSizeValidation sizeValidation;
	private ValidationAndConversionResponse validationResponse;
	
	@Before
	public void setUp(){
		sizeValidation = new PaginationPageSizeValidation(20);
	}
	
	@After
	public void tearDown(){
		sizeValidation = null;
		validationResponse = null;
	}
	
	@Test
	public void testIsValidMethodWithInputParamInteger(){
		validationResponse = sizeValidation.internalValidateAndConvert(5, null);
		Assert.assertEquals(true, validationResponse.isValid());
	}
	
	@Test
	public void testIsValidMethodWithInputParamString(){
		validationResponse = sizeValidation.internalValidateAndConvert("1", null);
		Assert.assertEquals(true, validationResponse.isValid());
	}
	
	@Test
	public void testIsValidMethodWithInputParamBoolanExpectedException(){
		validationResponse = sizeValidation.internalValidateAndConvert(true, null);
		Assert.assertEquals(false, validationResponse.isValid());
	}
	
	@Test
	public void testIsValidMethodWithInputParamStringWhichIsNotIntExpectedException(){
		validationResponse = sizeValidation.internalValidateAndConvert("true", null);
		Assert.assertEquals(false, validationResponse.isValid());
	}
}
