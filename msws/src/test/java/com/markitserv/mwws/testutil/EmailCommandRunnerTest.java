/**
 * 
 */
package com.markitserv.mwws.testutil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.markitserv.mwws.command.EmailCommand;
import com.markitserv.mwws.command.EmailCommandRunner;

/**
 * @author kiran.gogula
 *
 */
public class EmailCommandRunnerTest {
	
	private EmailCommandRunner emailCommandRunner;
	private EmailCommand emailCommand;

	@Before
	public void setUp(){
		emailCommandRunner = new EmailCommandRunner();
		emailCommand = new EmailCommand();
		emailCommand.setTo("kkirangogula.java@gmail.com");
		emailCommand.setFrom("kkirangogula.java@gmail.com");
		emailCommand.setSubject("Test Mail");
		emailCommand.setBody("Test Body");
	}
	
	@After
	public void tearDown(){
		emailCommand = null;
		emailCommandRunner = null;
	}
	
	@Test
	public void testSendMail(){
		emailCommandRunner.sendMail(emailCommand);
	}
}
