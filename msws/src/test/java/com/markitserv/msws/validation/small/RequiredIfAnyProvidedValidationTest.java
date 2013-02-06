/**
 * 
 */
package com.markitserv.msws.validation.small;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.markitserv.msws.validation.RequiredIfAnyProvidedValidation;
import com.markitserv.msws.validation.ValidationResponse;

/**
 * @author kiran.gogula
 *
 */
public class RequiredIfAnyProvidedValidationTest {
	
	private ValidationResponse validationResponse;
	private RequiredIfAnyProvidedValidation anyProvidedValidation;
	private Map<String, Object> map;
	
	@Before
	public void setUp(){
		anyProvidedValidation = new RequiredIfAnyProvidedValidation(new String[]{"string","list"});
		map = new HashMap<String, Object>();
		map.put("string", new String("String"));
		map.put("list", new ArrayList<String>().add("list"));
	}
	
	@After
	public void tearDown(){
		validationResponse = null;
		anyProvidedValidation = null;
		map = null;
	}
	
	@Test
	public void testIsValidMethodwithInputParmsMapAndTarget(){
		validationResponse = anyProvidedValidation.isValid("string", map);
		Assert.assertEquals(null, validationResponse);
	}
	
	@Test
	public void testIsValidMethodwithInputParmsMapAndTargetShouldBeNull(){
		validationResponse = anyProvidedValidation.isValid(null, map);
		Assert.assertEquals(false, validationResponse.isValid());
		
	}

}
