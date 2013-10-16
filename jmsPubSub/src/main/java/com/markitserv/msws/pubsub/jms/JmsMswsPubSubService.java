package com.markitserv.msws.pubsub.jms;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import com.markitserv.msws.pubsub.AbstractMswsPubSubService;
import com.markitserv.msws.pubsub.MswsEvent;

@Service
public class JmsMswsPubSubService extends AbstractMswsPubSubService {
	
	private class JmsMessagePostProcessor implements MessagePostProcessor {
		
		private String eventName;

		public JmsMessagePostProcessor(String eventName) {
			super();
			this.eventName = eventName;
		}

		@Override
		public Message postProcessMessage(Message message) throws JMSException {
			message.setStringProperty("eventName", eventName);
			return message;
		}
		
	}
	
	@Autowired
	private JmsTemplate template;
	
	@Override
	public void send(MswsEvent event) {
		
		JmsMessagePostProcessor postProcessor = new JmsMessagePostProcessor(event.getEventName());
		template.convertAndSend("msws.messageBus", event.getPayload(), postProcessor);
		
	}
} 