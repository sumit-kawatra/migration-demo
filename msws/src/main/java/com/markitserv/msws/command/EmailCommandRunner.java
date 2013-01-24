/**
 * 
 */
package com.markitserv.msws.command;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.exceptions.MswsException;
import com.markitserv.msws.exceptions.ProgrammaticException;

import static com.markitserv.msws.internal.MswsAssert.mswsAssert;

/**
 * @author kiran.gogula
 *
 */
@Service
public class EmailCommandRunner extends AbstractCommandRunner {

	Logger log = LoggerFactory.getLogger(EmailCommandRunner.class);
	
	@Autowired
	private String smtpHost;
	@Autowired
	private String smtpPort;
	
	
	
	private void sendMailMessage(EmailCommand emailCommand){
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
		Session session = Session.getInstance(props,null);
	    try{
		    Message message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(emailCommand.getFrom()));
		    message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailCommand.getTo()));
		    message.setSubject(emailCommand.getSubject());
		    message.setText(emailCommand.getBody());
		    Transport.send(message);
	    }catch (Exception e) {
	    	throw new ProgrammaticException("Exception from EmailCommandRunner", e);
		}	  
	}

	@Override
	public Object run(Command cmd) throws MswsException {
		 mswsAssert(cmd!=null, "EmailCommand must be set");
		 EmailCommand emailCommand = (EmailCommand) cmd;
		 sendMailMessage(emailCommand);
		 return null;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}
	
	

}
