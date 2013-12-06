package com.markitserv.msws.internal.request;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.stereotype.Service;

import com.markitserv.msws.beans.MswsRequestContext;
import com.markitserv.msws.util.MswsAssert;

/**
 * Spring Integration transformer that pulls the current MswsRequestContext from
 * the messaging layer and adds it to the current thread allowing the
 * MswsRequestContextHolder to access it.
 * 
 * @author roy.truelove
 * 
 */
@Service(value = "pullMswsRequestContextFromMessagingTransformer")
public class PullMswsRequestContextFromMessagingTransformer extends
		AbstractTransformer {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MswsRequestContextHelper helper;

	@Override
	protected Object doTransform(Message<?> message) throws Exception {

		MswsRequestContext ctx = message.getHeaders().get(
				MswsRequestContextHelper.MSG_HEADER_MSWS_REQ_CTX,
				MswsRequestContext.class);

		MswsAssert.mswsAssert(ctx != null,
				"MswsRequestContext could not be found on the message.  "
						+ "Ensure that it was added using the "
						+ "pushMswsRequestContextToMessagingTransformer "
						+ "transformer before crossing thread boundaries.");

		helper.registerRequestContextWithThread(ctx);

		return message;
	}
}
