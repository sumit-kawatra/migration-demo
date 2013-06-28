/**
 * 
 */
package com.markitserv.msws.validation.small;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.markitserv.msws.validation.AbstractValidation;
import com.markitserv.msws.validation.ForEachValidator;
import com.markitserv.msws.validation.RequiredValidation;
import com.markitserv.msws.validation.ValidationAndConversionResponse;

/**
 * @author kiran.gogula
 *
 */
public class ForEachValidatorTest {
	
	private ForEachValidator forEachValidator;
	private AbstractValidation forEachElement ;
	private ValidationAndConversionResponse validationResponse;
	private List<String> listItems;
	
	@Before
	public void setUp(){
		forEachElement = new RequiredValidation();
		forEachValidator = new ForEachValidator(forEachElement);
		listItems = new ArrayList<String>();
		listItems.add("a");
	} 
	
	@After
	public void tearDown(){
		validationResponse = null;
		forEachElement = null;
		validationResponse = null;
	}
	
	@Test
	public void testIsValidMethodwithInputList(){
		validationResponse = forEachValidator.validate(listItems, null);
		Assert.assertEquals(true, validationResponse.isValid());
	}
	
	@Test
	public void testIsValidMethodwithInputStringExpectedInvalid(){
		validationResponse = forEachValidator.validate("a", null);
		Assert.assertEquals(false, validationResponse.isValid());
	}
	
	@Test
	public void testIsValidMethodwithEmptyListExpctedInvalid(){
		List<String> Items = new ArrayList<String>();
		Items.add(" ");
		validationResponse = forEachValidator.validate(Items, null);
		Assert.assertEquals(false, validationResponse.isValid());
	}

}
