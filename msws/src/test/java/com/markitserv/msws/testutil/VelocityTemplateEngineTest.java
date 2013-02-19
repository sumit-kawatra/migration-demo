/**
 * 
 */
package com.markitserv.msws.testutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.markitserv.msws.command.ErrorCommand;
import com.markitserv.msws.exceptions.ProgrammaticException;
import com.markitserv.msws.util.VelocityTemplateEngine;

/**
 * @author kiran.gogula
 *
 */
public class VelocityTemplateEngineTest {
	
	private VelocityTemplateEngine templateEngine;
	private Map<String, Object> paramMap = null;
	
	@Before
	public void setUp(){
		templateEngine = new VelocityTemplateEngine();
		paramMap = new HashMap<String, Object>();
		
		paramMap.put("name", "Test Velocity");
		paramMap.put("allProducts", new String[]{"FX","Credit","Swap"});
		paramMap.put("errorCommandList", createErrorCommandtList());
		paramMap.put("firstName", "");
	}
	
	private List<ErrorCommand> createErrorCommandtList(){
		List<ErrorCommand> list = new ArrayList<ErrorCommand>();
		for (int i = 0; i < 5; i++) {
			ErrorCommand error = new ErrorCommand();
			error.setErrorMessage("Error Message"+i);
			list.add(error);
		}
		return list;
	}
	
	@After
	public void teatDown(){
		templateEngine = null;
		paramMap = null;
	}
	
	@Test
	public void testGenerateStringFromTemplateMethodWithDifferentTypeOfPlaceHolders(){
		templateEngine.afterPropertiesSet();
		String result = templateEngine.generateStringFromTemplate("testTemplate", paramMap);
		System.out.println(result);
		Assert.assertNotNull(result);
	}
	
	@Test(expected=ProgrammaticException.class)
	public void testGenerateStringFromTemplateMethodWithEmptyTemplateName(){
		templateEngine.generateStringFromTemplate("", paramMap);
	}

	
	@Test(expected=ProgrammaticException.class)
	public void testGenerateStringFromTemplateMethodWithEmptyParamMap(){
		templateEngine.generateStringFromTemplate("testTemplate", null);
	}
	
	
}
