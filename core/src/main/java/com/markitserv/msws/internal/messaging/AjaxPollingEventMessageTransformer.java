package com.markitserv.msws.internal.messaging;

import org.joda.time.DateTime;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.Transformer;

import com.markitserv.msws.beans.AjaxPollingEvent;
import com.markitserv.msws.internal.exceptions.ProgrammaticException;
import com.markitserv.msws.messaging.AjaxPollingQueue;
import com.markitserv.msws.messaging.NullPayload;

/**
 * Converts message payload to a AjaxPollingEvent if it isn't already one. If it
 * is not already an AjaxPollingEvent then it expects that there is a header on
 * the message specifiying the type of the ajax event
 * (AjaxPollingQueue.MSG_HEADER_AJAX_POLLING_EVENT_TYPE)
 * 
 * @author roy.truelove
 * 
 */
public class AjaxPollingEventMessageTransformer implements Transformer {

	@Override
	public Message<?> transform(Message<?> message) {

		Message<?> newMessage = null;

		Object payload = message.getPayload();

		// it's either an instance of LongPollingEvent already, or..
		if (payload instanceof AjaxPollingEvent) {
			newMessage = message;
		} else {

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

			newMessage = this.buildLongPollingEventMsg(message);
		}

		newMessage = this.stripNullPayloads(newMessage);

		return newMessage;

	}

	/**
	 * Converts NullPayload to a 'null'
	 * 
	 * @param newMessage
	 * @return
	 */
	private Message<?> stripNullPayloads(Message<?> message) {

		AjaxPollingEvent<?> event = (AjaxPollingEvent<?>) message.getPayload();

		if (!(event.getPayload() instanceof NullPayload)) {
			return message;
		}

		event.setPayload(null);

		return MessageBuilder.withPayload(event)
				.copyHeaders(message.getHeaders()).build();

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
		env.setId(headers.getId().toString());

		return MessageBuilder.withPayload(env).copyHeaders(headers).build();

	}

}
