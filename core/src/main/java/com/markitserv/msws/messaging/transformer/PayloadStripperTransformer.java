package com.markitserv.msws.messaging.transformer;

import java.util.Map;

import org.springframework.integration.Message;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.stereotype.Service;

import com.markitserv.msws.beans.AjaxPollingEvent;
import com.markitserv.msws.internal.filters.AjaxEventTypeFilter;
import com.markitserv.msws.messaging.NullPayload;

/**
 * Sets the payload of a message to a NullPayload. This is useful when the
 * information in the message headers suffice and no payload is necessary.
 * Commonly used for ajax events.
 * 
 * @author roy.truelove
 * 
 */
@Service(value = "payloadStripperTransformer")
public class PayloadStripperTransformer {

	@Transformer
	public Message<?> stripPayload(Message<?> message) throws Exception {

		Message<?> newMessage = MessageBuilder.withPayload(new NullPayload())
				.copyHeaders((Map<String, ?>) message.getHeaders()).build();

		return newMessage;
	}

}
