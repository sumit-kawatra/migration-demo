package com.markitserv.msws.internal.request;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.interceptor.ChannelInterceptorAdapter;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.stereotype.Service;

import com.markitserv.msws.request.MswsRequestContextHolder;

/**
 * Spring Integration transformer that puts the current MswsRequestContext as a
 * message header. This should be used in the messaging layer when crossing
 * thread boundaries
 * 
 * @author roy.truelove
 * 
 */
@Service(value = "pushMswsRequestContextToMessagingTransformer")
public class PushMswsRequestContextToMessagingTransformer extends
		AbstractTransformer {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MswsRequestContextHolder holder;

	@Override
	protected Object doTransform(Message<?> message) throws Exception {
		Message<?> newMsg = MessageBuilder
				.fromMessage(message)
				.setHeader(MswsRequestContextHelper.MSG_HEADER_MSWS_REQ_CTX,
						holder.getRequestContext()).build();

		log.info("Added MswsRequestContext to message: " + newMsg.toString());

		return newMsg;
	}
}
