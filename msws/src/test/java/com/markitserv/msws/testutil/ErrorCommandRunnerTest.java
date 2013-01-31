/**
 * 
 */
package com.markitserv.msws.testutil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.markitserv.msws.command.CommandDispatcher;
import com.markitserv.msws.command.ErrorCommand;
import com.markitserv.msws.command.ErrorCommandRunner;
import com.markitserv.msws.exceptions.AssertionException;

/**
 * @author kiran.gogula
 *
 */
public class ErrorCommandRunnerTest {
	
	private ErrorCommandRunner errorCommandRunner;
	private ErrorCommand errorCommand;
	private CommandDispatcher commandDispatcher;
	
	
	@Before
	public void setUp(){
		errorCommandRunner = new ErrorCommandRunner();
		errorCommandRunner.setEnv("DEV");
		errorCommandRunner.setErrorDistributionGroup("to@localhost.com");
		errorCommand = new ErrorCommand() ;
		errorCommand.setErrorMessage("Error Message");	
		commandDispatcher = Mockito.mock(CommandDispatcher.class);
		errorCommandRunner.setDispatcher(commandDispatcher);
	}
	
	@After
	public void tearDown(){
		errorCommand = null;
		errorCommandRunner = null;
		commandDispatcher = null;
	}
	
	@Test(expected = AssertionException.class)
	public void testInputErrorCommandIsNull(){
		errorCommandRunner.run(null);
	}
	
	
	
	
	@Test 
	public void testRun(){
		Object obj = errorCommandRunner.run(errorCommand);
		Assert.assertEquals(null, obj);
	}

}
