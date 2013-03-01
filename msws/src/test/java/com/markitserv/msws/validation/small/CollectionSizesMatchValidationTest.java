/**
 * 
 */
package com.markitserv.msws.validation.small;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.markitserv.msws.validation.CollectionSizesMatchValidation;
import com.markitserv.msws.validation.ValidationAndConversionResponse;

/**
 * @author kiran.gogula
 *
 */
public class CollectionSizesMatchValidationTest {

	
	private CollectionSizesMatchValidation sizesMatchValidation;
	private ValidationAndConversionResponse response;
	private Map<String, Object> map;
	private List<String> list;
	
	@Before
	public void setUp(){
		sizesMatchValidation = new CollectionSizesMatchValidation("list");
		map = new HashMap<String, Object>();
		list = new ArrayList<String>();
		list.add("foo");
		map.put("list", list);
	}
	
	@After
	public void tearDown(){
		sizesMatchValidation = null;
		map = null;
		response = null;
		list = null;
	}
	
	@Test
	public void testIsValidMethodWithProperInputValueList(){
		response = sizesMatchValidation.validate(list, map);
		Assert.assertEquals(true, response.isValid());
	}
	
	@Test
	public void testIsValidMethodWithEmptyMapExpectedException(){
		map = new HashMap<String, Object>();
		response = sizesMatchValidation.validate(list, map);
		Assert.assertEquals(false, response.isValid());
	}
	
	@Test
	public void testIsValidMethodWithDifferentListSizeExpectedException(){
		map = new HashMap<String, Object>();
		List<String> lst = new ArrayList<String>();
		lst.add("foo");
		lst.add("doo");
		map.put("list", lst);
		response = sizesMatchValidation.validate(list, map);
		Assert.assertEquals(false, response.isValid());
	}
	
	
}
