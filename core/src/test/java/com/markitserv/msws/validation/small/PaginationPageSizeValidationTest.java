/**
 * 
 */
package com.markitserv.msws.validation.small;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.markitserv.msws.validation.PaginationPageSizeValidation;
import com.markitserv.msws.validation.ValidationResponse;

/**
 * @author kiran.gogula
 *
 */
public class PaginationPageSizeValidationTest {
	
	private PaginationPageSizeValidation sizeValidation;
	private ValidationResponse validationResponse;
	
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
		validationResponse = sizeValidation.validateInternal(5, null);
		Assert.assertEquals(true, validationResponse.isValid());
	}
	
	@Test
	public void testIsValidMethodWithInputParamString(){
		validationResponse = sizeValidation.validateInternal("1", null);
		Assert.assertEquals(true, validationResponse.isValid());
	}
	
	@Test
	public void testIsValidMethodWithInputParamBoolanExpectedException(){
		validationResponse = sizeValidation.validateInternal(true, null);
		Assert.assertEquals(false, validationResponse.isValid());
	}
	
	@Test
	public void testIsValidMethodWithInputParamStringWhichIsNotIntExpectedException(){
		validationResponse = sizeValidation.validateInternal("true", null);
		Assert.assertEquals(false, validationResponse.isValid());
	}
}
