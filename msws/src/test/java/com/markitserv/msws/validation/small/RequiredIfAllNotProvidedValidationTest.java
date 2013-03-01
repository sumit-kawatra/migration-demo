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

import com.markitserv.msws.validation.RequiredIfAllNotProvidedValidation;
import com.markitserv.msws.validation.ValidationAndConversionResponse;

/**
 * @author kiran.gogula
 *
 */
public class RequiredIfAllNotProvidedValidationTest {
	
	private RequiredIfAllNotProvidedValidation validation;
	private ValidationAndConversionResponse response;
	private Map<String, Object> map;
	
	@Before
	public void setUp(){
		validation = new RequiredIfAllNotProvidedValidation(new String[]{"ParticipantId","UserName"});
		map = new HashMap<String, Object>();
		map.put("UserName", new String("Kiran"));
		map.put("ParticipantId", new String("2"));
	}
	
	@After
	public void tearDown(){
		validation = null;
		response = null;
		map = null;
	}
	
	@Test
	public void testIsValidMethodWithOtherParamValue(){
		response = validation.validate("Kiran", map);
		Assert.assertTrue(response.isValid());
	}
	
	@Test
	public void testIsValidMethodWithTargetAndOtherParamValue(){
		map = new HashMap<String, Object>();
		response = validation.validate(null, map);
		Assert.assertFalse(response.isValid());
	}

}
