/**
 * 
 */
package com.markitserv.mwws.command;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.markitserv.mwws.exceptions.MwwsException;

/**
 * @author kiran.gogula
 *
 */
@Service
public class EmailCommandRunner extends AbstractCommandRunner {

	Logger log = LoggerFactory.getLogger(EmailCommandRunner.class);
	
	
	public void sendMail(AsyncCommand cmd) throws MwwsException {
		if(cmd  != null){
			EmailCommand emailCommand = (EmailCommand) cmd;
			 sendMailMessage(emailCommand);
		}
	}
	
	private void sendMailMessage(EmailCommand emailCommand){
		Properties props = new Properties();
		props.put("mail.smtp.host", "");
		props.put("mail.smtp.port", "");
		Session session = Session.getInstance(props,null);
	    try{
		    Message message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(emailCommand.getFrom()));
		    message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailCommand.getTo()));
		    message.setSubject(emailCommand.getSubject());
		    message.setText(emailCommand.getBody());
		    Transport.send(message);
	    }catch (Exception e) {
	    	log.error("Exception from email command runner class: ", e);
		}	  
	}

	@Override
	protected Object run(Command cmd) throws MwwsException {
		return null;
	}

}
