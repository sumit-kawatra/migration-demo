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

import com.markitserv.msws.validation.CollectionSizeValidation;
import com.markitserv.msws.validation.ValidationResponse;

/**
 * @author kiran.gogula
 *
 */
public class CollectionSizeValidationTest {

	private CollectionSizeValidation collectionSizeValidation;
	private ValidationResponse response;
	
	@Before
	public void setUp(){
		collectionSizeValidation = new CollectionSizeValidation(1, 2);
	}
	
	@After
	public void tearDown(){
		collectionSizeValidation = null;
		response = null;
	}
	
	@Test
	public void testIsValidMethodWithProperMinAndMaxValues(){
		List<String> list = new ArrayList<String>();
		list.add("foo");
		list.add("too");
		response = collectionSizeValidation.validate(list, null);
		Assert.assertEquals(true, response.isValid());
	}
	
	@Test
	public void testIsValidMethodTargerIsStringExpectedInvalid(){
		response = collectionSizeValidation.validate("a", null);
		Assert.assertEquals(false, response.isValid());
	}
	
	@Test
	public void testIsValidMethodWithExceedMaxValues(){
		List<String> list = new ArrayList<String>();
		list.add("foo");
		list.add("too");
		list.add("too");
		response = collectionSizeValidation.validate(list, null);
		Assert.assertEquals(false, response.isValid());
	}
	
	@Test
	public void testIsValidMethodWithLessThandtMinValues(){
		List<String> list = new ArrayList<String>();		
		response = collectionSizeValidation.validate(list, null);
		Assert.assertEquals(false, response.isValid());
	}
}
