/**
 * 
 */
package com.markitserv.msws.testutil;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.markitserv.msws.command.EmailCommand;
import com.markitserv.msws.command.EmailCommandRunner;
import com.markitserv.msws.exceptions.AssertionException;
import com.markitserv.msws.exceptions.ProgrammaticException;

/**
 * @author kiran.gogula
 *
 */
public class EmailCommandRunnerTest {
	
	private EmailCommandRunner emailCommandRunner;
	private EmailCommand emailCommand;
	private GreenMail greenMail;

	@Before
	public void setUp(){
		greenMail = new GreenMail(new ServerSetup(3025, null, "smtp"));
		emailCommandRunner = new EmailCommandRunner();
		emailCommandRunner.setSmtpHost("localhost");
		emailCommandRunner.setSmtpPort("3025");
		emailCommand = new EmailCommand() ;
		emailCommand.setTo("kkirangogula.java@gmail.com");
		emailCommand.setFrom("kkirangogula.java@gmail.com");
		emailCommand.setSubject("Test Mail");
		emailCommand.setBody("Test Body");
		
	}
	
	@After
	public void tearDown(){
		emailCommand = null;
		emailCommandRunner = null;
		greenMail = null;
	}
	
	
	@Test(expected = ProgrammaticException.class)
	public void testSendMailProgrammaticExcetion(){
		emailCommandRunner.run(emailCommand);
	}
	
	
	@Test(expected = AssertionException.class)
	public void testSendMailAssertionException(){
		emailCommandRunner.run(null);
	}
	
	@Test
	public void testSendMail() throws Exception{
		greenMail.start();
		emailCommandRunner.run(emailCommand);
		MimeMessage msg[] = greenMail.getReceivedMessages();
		for (MimeMessage mimeMessage : msg) {
			String subject = mimeMessage.getSubject();
			Address address[] = mimeMessage.getRecipients(Message.RecipientType.TO);
			Assert.assertEquals("Test Mail", subject);
			Assert.assertEquals("kkirangogula.java@gmail.com", address[0].toString());
			Assert.assertEquals("kkirangogula.java@gmail.com", address[0].toString());
		}
		greenMail.stop();
	}
	
}
