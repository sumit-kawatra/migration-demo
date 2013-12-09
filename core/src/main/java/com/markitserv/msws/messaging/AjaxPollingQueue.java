package com.markitserv.msws.messaging;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.NotImplementedException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessageHandler;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.core.SubscribableChannel;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.filter.MessageFilter;
import org.springframework.integration.handler.BridgeHandler;
import org.springframework.integration.handler.MessageHandlerChain;
import org.springframework.integration.transformer.MessageTransformingHandler;
import org.springframework.integration.transformer.Transformer;

import com.markitserv.msws.beans.AjaxPollingEvent;
import com.markitserv.msws.exceptions.InvalidActionParamValueException;
import com.markitserv.msws.internal.exceptions.PollingTimeoutException;
import com.markitserv.msws.internal.exceptions.ProgrammaticException;
import com.markitserv.msws.internal.messaging.AjaxPollingEventMessageTransformer;
import com.markitserv.msws.request.MswsRequestContextHolder;
import com.markitserv.msws.util.MswsAssert;

/**
 * Provides a wrapper around a Spring Integration pubsub queue, intended to be
 * used by PollingActions to feed the messages of that queue back to the client.
 * Expecting an instance of this to be injected into every long polling action.
 * Expected to be in session scope!!!
 * 
 * @author roy.truelove
 * 
 */
// @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
// @Service
public class AjaxPollingQueue implements DisposableBean, InitializingBean,
		ApplicationContextAware, BeanNameAware, BeanFactoryAware {

	Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * If the payload on the input channel is not an AjaxPollingEvent, then this
	 * message header is required to specify the type of event.
	 */
	public static final Object MSG_HEADER_AJAX_POLLING_EVENT_TYPE = "AJAX_POLLING_EVENT_TYPE";

	// injected by spring
	private WebApplicationContext ctx;
	private String beanName;
	private DefaultListableBeanFactory beanFactory;

	// props
	private String inputChannelName;
	private long timeout = 30000; // defaults to 30 seconds
	private boolean allowMultipleConnectionsPerSession = false;
	private AjaxPollingPreFilterMessageSelector preFilter = null;

	@Autowired
	private MswsRequestContextHolder reqCtxHolder;

	private MessageChannel perSessionChannel;
	private EventDrivenConsumer _bridgeConsumer;

	public List<AjaxPollingEvent<?>> receive(Boolean blocks) {

		checkIsSessionBean();

		if (!allowMultipleConnectionsPerSession) {
			if (blocks) {
				return receiveBlockedQueueMessages();
			} else {
				return receiveUnblockedQueueMessages();
			}
		} else {
			throw new NotImplementedException(
					"Multiple Session Queues not yet supported");
			// if (blocks) {
			// } else {
			// throw new InvalidActionParamValueException(
			// "Blocking on this action is required as it can have multiple connections per session.");
			// }
		}
	}

	// private LinkedList<AjaxPollingEvent<?>> receiveBlockedPubsubMessages() {
	//
	// PublishSubscribeChannel channel = (PublishSubscribeChannel)
	// perSessionChannel;
	//
	// QueueChannel q = new QueueChannel();
	// q.
	//
	// msgTemplate.receive(channel);
	//
	// }

	private LinkedList<AjaxPollingEvent<?>> receiveUnblockedQueueMessages() {

		QueueChannel channel = (QueueChannel) perSessionChannel;

		LinkedList<AjaxPollingEvent<?>> msgs = new LinkedList<AjaxPollingEvent<?>>();
		List<Message<?>> allMessages = channel.clear();
		for (Message<?> message : allMessages) {
			msgs.addLast(getAjaxPollingEventFromMessage(message));
		}
		return msgs;
	}

	private LinkedList<AjaxPollingEvent<?>> receiveBlockedQueueMessages() {
		LinkedList<AjaxPollingEvent<?>> msgs = new LinkedList<AjaxPollingEvent<?>>();

		QueueChannel channel = (QueueChannel) perSessionChannel;

		// wait for the first message. Unf. there's no way to peek.
		// Blocks.
		Message<?> msg = channel.receive(timeout);

		if (msg == null) {
			return msgs; // when it times out, will return an empty array of
							// messages.
		}

		msgs.addLast(getAjaxPollingEventFromMessage(msg));

		// empty the queue. Timeout of 0 returns instantly if the queue is
		// empty
		if (channel.getQueueSize() > 0) {
			// note! that sometimes that will return null even when there
			// are messages. The client will need to re-query for the
			// remaining. This is an issue with the underlying queue.
			while ((msg = channel.receive(0)) != null) {
				msgs.addLast(getAjaxPollingEventFromMessage(msg));
			}
		}
		return msgs;
	}

	private AjaxPollingEvent<?> getAjaxPollingEventFromMessage(Message<?> msg) {
		// expects that the transformer has already converted it to an
		// AjaxPollingEvent.
		return (AjaxPollingEvent<?>) msg.getPayload();
	}

	private void setupQueue() {

		// Create queue
		// if we allow multiple connections for a given session we cannot use a
		// queue. Has to be a pubsub.
		if (!this.allowMultipleConnectionsPerSession) {
			perSessionChannel = new QueueChannel();
		} else {
			throw new NotImplementedException();
			// perSessionChannel = new PublishSubscribeChannel();
		}

		// get the ajax pubsub channel
		SubscribableChannel inputChannel = (SubscribableChannel) ctx
				.getBean(inputChannelName);

		LinkedList<MessageHandler> handlers = new LinkedList<MessageHandler>();

		// Converts the payload of this message to an instance of
		// AjaxPollingEvent, if it isn't already one.
		MessageTransformingHandler toAjaxEventTransformingHandler = new MessageTransformingHandler(
				new AjaxPollingEventMessageTransformer());

		handlers.addLast(toAjaxEventTransformingHandler);

		// If there's a prefilter selector, this will filter it.
		if (this.preFilter != null) {
			preFilter.registerMswsRequestContext(reqCtxHolder
					.getRequestContext());
			MessageFilter f = new MessageFilter(preFilter);
			handlers.addLast(f);
		}

		MessageHandlerChain handlerChain = new MessageHandlerChain();
		handlerChain.setHandlers(handlers);

		handlerChain.setOutputChannel(perSessionChannel);

		_bridgeConsumer = new EventDrivenConsumer(inputChannel, handlerChain);
		_bridgeConsumer.start();

	}

	private void teardownQueue() {
		_bridgeConsumer.stop();
	}

	// ************************************************* Spring stuff

	@Override
	public void afterPropertiesSet() throws Exception {
		log.error("SESSION BEAN CREATED");
		setupQueue();
	}

	@Override
	public void destroy() throws Exception {
		log.error("SESSION BEAN DESTROYED");
		teardownQueue();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {

		MswsAssert.mswsAssert(
				applicationContext instanceof WebApplicationContext, "Bean '"
						+ this.beanName
						+ "' can only be used within a web context");

		this.ctx = (WebApplicationContext) applicationContext;

	}

	@Override
	public void setBeanFactory(BeanFactory bFactory) throws BeansException {
		this.beanFactory = (DefaultListableBeanFactory) bFactory;

	}

	private void checkIsSessionBean() {
		BeanDefinition def = this.beanFactory.getBeanDefinition(this.beanName);
		String scope = def.getScope();
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	// ************************************************* INJECTED PROPERTIES

	/**
	 * The name of the pubsub queue that will contain messages intended for the
	 * client. The payload for this channel must be a LongPollingEvent, OR, a
	 * Spring Integration Message that has a headers called
	 * "LONG_POLLING_EVENT_TYPE"
	 * 
	 * @param inputChannelName
	 */
	@Required
	public void setInputChannelName(String inputChannelName) {
		this.inputChannelName = inputChannelName;
	}

	/**
	 * In the case a blocking call, how long the receive calls will block before
	 * throwing a PollingTimeoutException. This exception is benign, it's simply
	 * there to let the client know that they'll need to reconnect if they need
	 * more messages. Defaults to 30 seconds
	 * 
	 * @param timeout
	 *            in milliseconds
	 */
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public void setAllowMultipleConnectionsPerSession(
			boolean allowMultipleConnectionsPerSession) {
		this.allowMultipleConnectionsPerSession = allowMultipleConnectionsPerSession;
	}

	public void setPreFilter(AjaxPollingPreFilterMessageSelector preFilter) {
		this.preFilter = preFilter;
	}

}
