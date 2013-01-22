/**
 * 
 */
package com.markitserv.mwws.emailCommand;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
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
public class EmailCommandRunner extends AbstractEmailCommandRunner {

	Logger log = LoggerFactory.getLogger(EmailCommandRunner.class);
	
	@Override
	public Object sendMail(EmailCommand cmd) throws MwwsException {
		if(cmd  != null){
			return sendMailMessage(cmd);
		}
		return null;
	}
	
	private String sendMailMessage(EmailCommand emailCommand){
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
		    return "Mail was sent successfully";
	    }catch (Exception e) {
	    	log.error("Exception from email command runner class: ", e);
		}	  
		return null;
	}

}
