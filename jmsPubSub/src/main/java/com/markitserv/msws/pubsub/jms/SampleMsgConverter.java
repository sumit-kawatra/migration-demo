package com.markitserv.msws.pubsub.jms;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.core.serializer.Serializer;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

@Service(value = "JmsMessageConverter")
public class SampleMsgConverter implements MessageConverter {

	@Override
	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {

		if (object instanceof Serializable) {
			return session.createObjectMessage((Serializable) object);
		} else {
			throw new MessageConversionException(
					"Got a message with payload of type "
							+ object.getClass().getCanonicalName()
							+ ", which is not serializable.");
		}
	}

	@Override
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		throw new MessageConversionException("Couldn't do it..");
	}
}
