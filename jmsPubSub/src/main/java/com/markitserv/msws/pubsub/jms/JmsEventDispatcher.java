package com.markitserv.msws.pubsub.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.exceptions.ProgrammaticException;
import com.markitserv.msws.internal.MswsAssert;
import com.markitserv.msws.messaging.AbstractMswsPubSubService;
import com.markitserv.msws.messaging.Event;

@Service(value = "eventDispatcher")
public class JmsEventDispatcher implements MessageListener {
	
	@Autowired
	AbstractMswsPubSubService svc;

	@Override
	public void onMessage(Message message) {

		try {
			safeOnMessage(message);
		} catch (JMSException e) {
			throw new ProgrammaticException("Could not dispatch JMS message", e);
		}
	}

	public void safeOnMessage(Message message) throws JMSException {

		// Make an event out of the message
		
		String eventName = message.getStringProperty("eventName");
		
		MswsAssert.mswsAssert(eventName != null,
				"eventName property not found in JMS message: %s",
				message.toString());
		
//		Event event = new Event(eventName);
//		event.setPayload(((ObjectMessage) message).getObject());
		
//		svc.dispatchEvent(event);
	}
}
