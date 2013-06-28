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

import com.markitserv.msws.exceptions.AssertionException;
import com.markitserv.msws.validation.SortByValidation;
import com.markitserv.msws.validation.ValidationAndConversionResponse;

/**
 * @author kiran.gogula
 *
 */
public class SortByValidationTest {
	
	SortByValidation sortByValidation;
	ValidationAndConversionResponse validationResponse;
	List<String> itemList;
	
	@Before
	public void setUp(){
		sortByValidation = new SortByValidation(new String[]{"foo","boo","too"});
		itemList = new ArrayList<String>();
		itemList.add("foo");
		itemList.add("boo");
		itemList.add("too");
		
	}
	
	@After
	public void tearDown(){
		sortByValidation = null;
		validationResponse = null;
		itemList = null;
	}
	
	@Test
	public void testIsValidWithStringInputWhichIsTobeSearched(){
		validationResponse = sortByValidation.validate("foo", null);
		Assert.assertEquals(true, validationResponse.isValid());
	}

	
	
	@Test(expected=AssertionException.class)
	public void testIsValidWithException(){
		validationResponse = sortByValidation.validate(1, null);
	}
	
	@Test(expected=AssertionException.class)
	public void testIsValidCheckConstructorArgumentWithException(){
		sortByValidation = new SortByValidation(new String[]{"","",""});
		validationResponse  = sortByValidation.validate(1, null);
	}
	
	@Test
	public void testIsValidMethodWithStringInputWhichIsNotPresent(){
		validationResponse = sortByValidation.validate("koo", null);
		Assert.assertEquals(false, validationResponse.isValid());
	}
	
	@Test
	public void testIsValidMethodWithConstructorArgAsList(){
		sortByValidation = new SortByValidation(itemList);
		validationResponse = sortByValidation.validate("foo", null);
		Assert.assertEquals(true, validationResponse.isValid());
	}
}
