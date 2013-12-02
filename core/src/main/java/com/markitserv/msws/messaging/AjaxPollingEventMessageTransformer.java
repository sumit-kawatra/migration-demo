package com.markitserv.msws.messaging;

import org.joda.time.DateTime;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.Transformer;

import com.markitserv.msws.beans.AjaxPollingEvent;
import com.markitserv.msws.internal.exceptions.ProgrammaticException;

/**
 * Converts message payload to a AjaxPollingEvent if it isn't already one.
 * 
 * @author roy.truelove
 * 
 */
public class AjaxPollingEventMessageTransformer implements Transformer {

	@Override
	public Message<?> transform(Message<?> message) {
		Object payload = message.getPayload();

		// it's either an instance of LongPollingEvent already, or..
		if (payload instanceof AjaxPollingEvent) {
			return message;
		}

		// it has a header with the event type and we build the Event.
		MessageHeaders headers = message.getHeaders();
		String eventType = headers.get(
				AjaxPollingQueue.MSG_HEADER_AJAX_POLLING_EVENT_TYPE,
				String.class);

		if (eventType == null) {
			throw new ProgrammaticException(
					"All messages on Ajax event pubsub channels must "
							+ "either be an instance of AjaxPollingEvent, or, must have the "
							+ AjaxPollingQueue.MSG_HEADER_AJAX_POLLING_EVENT_TYPE
							+ " header set to the event type.");
		}

		return this.buildLongPollingEventMsg(message);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Message<AjaxPollingEvent> buildLongPollingEventMsg(Message msg) {

		MessageHeaders headers = msg.getHeaders();
		String eventType = headers.get(
				AjaxPollingQueue.MSG_HEADER_AJAX_POLLING_EVENT_TYPE,
				String.class);
		long time = headers.getTimestamp();

		AjaxPollingEvent env = new AjaxPollingEvent();

		env.setPayload(msg.getPayload());
		env.setEventType(eventType);
		// TODO convert this to UTC!
		env.setTimestamp(new DateTime(time));

		return MessageBuilder.fromMessage(msg).withPayload(env).build();

	}

}
