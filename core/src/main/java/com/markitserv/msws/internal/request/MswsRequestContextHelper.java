package com.markitserv.msws.internal.request;

import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.stereotype.Service;
import com.markitserv.msws.beans.MswsRequestContext;
import com.markitserv.msws.util.MswsAssert;

/**
 * 'Internal' helper class for the MswsRequestContext
 * 
 * @author roy.truelove
 * 
 */
@Service
public class MswsRequestContextHelper {

	public static final String MSG_HEADER_MSWS_REQ_CTX = "MSWS_REQ_CTX";

	private static final ThreadLocal<MswsRequestContext> mswsReqThreadLocal = new ThreadLocal<MswsRequestContext>();

	public MswsRequestContext getMswsRequestContextFromThread() {

		MswsRequestContext ctx = mswsReqThreadLocal.get();
		MswsAssert
				.mswsAssert(
						ctx != null,
						"No MswsRequestContext found on the thread.  This will "
								+ "often be the case if you are trying to access "
								+ "the request context in a thread where it has "
								+ "not been set.  If you're using Spring "
								+ "Integration to create threads, see the MswsRequestContextPropagatorInterceptor class");
		return ctx;

	}

	/**
	 * Adds the request context to the request object and to a thread local
	 * 
	 * @param requestContext
	 */
	public void registerRequestContextWithThread(
			MswsRequestContext requestContext) {

		// Add to both the request and the thread local
		mswsReqThreadLocal.set(requestContext);

	}
}
