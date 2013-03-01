/**
 * 
 */
package com.markitserv.msws.validation.small;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.markitserv.msws.validation.OptionalValidation;
import com.markitserv.msws.validation.ValidationAndConversionResponse;

/**
 * @author kiran.gogula
 *
 */
public class OptionalValidationTest {
	
	private OptionalValidation optionalValidation;
	private ValidationAndConversionResponse response;
	
	@Before
	public void setUp(){
		optionalValidation = new OptionalValidation();
	}
	
	@After
	public void tearDown(){
		optionalValidation = null;
		response = null;
	}
	
	
	@Test
	public void testIsValidateMethod(){
		response = optionalValidation.validate("", null);
		Assert.assertEquals(true, response.isValid());
	}

}
