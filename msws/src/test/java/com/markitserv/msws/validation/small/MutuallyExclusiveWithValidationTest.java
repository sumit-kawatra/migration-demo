/**
 * 
 */
package com.markitserv.msws.validation.small;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.markitserv.msws.validation.MutuallyExclusiveWithValidation;
import com.markitserv.msws.validation.ValidationAndConversionResponse;

/**
 * @author kiran.gogula
 *
 */
public class MutuallyExclusiveWithValidationTest {
	
	private MutuallyExclusiveWithValidation validation;
	private ValidationAndConversionResponse response;
	private Map<String, Object> map;
	
	@Before
	public void setUp(){
		validation = new MutuallyExclusiveWithValidation(new String[]{"ParticipantId"});
		map = new HashMap<String, Object>();
		map.put("ParticipantId", new String("2"));
	}
	
	@After
	public void tearDown(){
		validation = null;
		response = null;
		map = null;
	}
	
	@Test
	public void testIsValidMethodWithTargetProvidedThenOtherValuesAreNotNeeded(){
		response = validation.validate("UserName", map);
		Assert.assertFalse(response.isValid());
	}
	
	@Test
	public void testIsValidMethodWithOneInputParam(){
		map = new HashMap<String, Object>();
		response = validation.validate("UserName", map);
		Assert.assertTrue(response.isValid());
	}
	
	@Test
	public void testIsValidMethodWithTargetProvidedThenMultipleOtherValuesAreNotNeeded(){
		validation = new MutuallyExclusiveWithValidation(new String[]{"ParticipantId", "BookName"});
		map.put("BookName", new String("Book"));
		response = validation.validate("UserName", map);
		Assert.assertFalse(response.isValid());
	}

}
