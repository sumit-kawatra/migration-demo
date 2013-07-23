/**
 * 
 */
package com.markitserv.msws.validation.small;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.markitserv.msws.validation.DateValidation;
import com.markitserv.msws.validation.IntegerValidation;
import com.markitserv.msws.validation.ValidationResponse;

/**
 * @author kiran.gogula
 *
 */
public class DateValidationTest {
	
	private DateValidation dateValidation;
	private ValidationResponse validationResponse;
	
	@Before
	public void setUp(){
		dateValidation = new DateValidation();
	}
	
	@After
	public void tearDown(){
		validationResponse = null;
		dateValidation = null;
	}
	
	@Test
	public void dateValueIsValid(){
		validationResponse = dateValidation.validateInternal(new DateTime(), null);
		Assert.assertTrue(validationResponse.isValid());
	}
	
	@Test
	public void dateValueInGoodStringFormatIsValid(){
		validationResponse = dateValidation.validateInternal("2013-07-22", null);
		Assert.assertTrue(validationResponse.isValid());
	}
	
	@Test
	public void dateValueInBadStringFormatIsInvalid(){
		validationResponse = dateValidation.validateInternal("Not a Date", null);
		Assert.assertFalse(validationResponse.isValid());
	}
	
	@Test
	public void dateValueInUnexpectedDateFormatIsInvalid(){
		validationResponse = dateValidation.validateInternal("04/02/2011", null);
		Assert.assertFalse(validationResponse.isValid());
	}
	
	public void unexpectedTypeIsInvalid() {
		validationResponse = dateValidation.validateInternal(new Integer(1), null);
		Assert.assertFalse(validationResponse.isValid());
	}
	
	@Test
	public void canConvertStringToDateTime(){
		
		DateTime expected = new DateTime("2013-07-22");
		
		validationResponse = dateValidation.validateInternal("2013-07-22", null);
		Assert.assertEquals(expected, validationResponse.getConvertedObj());
	}
	
	@Test
	public void cantConvertNonIntStringIntToInt(){
		validationResponse = dateValidation.validateInternal("Sup", null);
		Assert.assertNull(validationResponse.getConvertedObj());
		Assert.assertFalse(validationResponse.isValid());
	}
}
