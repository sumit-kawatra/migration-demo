package com.markitserv.msws.messaging;

import java.util.LinkedList;
import java.util.List;

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
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.SubscribableChannel;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.handler.BridgeHandler;

import com.markitserv.msws.beans.AjaxPollingEvent;
import com.markitserv.msws.internal.exceptions.PollingTimeoutException;
import com.markitserv.msws.internal.exceptions.ProgrammaticException;
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

	private QueueChannel queue;
	private EventDrivenConsumer _bridgeConsumer;

	public List<AjaxPollingEvent<?>> receive(Boolean blocks) {

		checkIsSessionBean();

		LinkedList<AjaxPollingEvent<?>> msgs = new LinkedList<AjaxPollingEvent<?>>();

		if (blocks) {

			// wait for the first message. Unf. there's no way to peek.
			Message<?> msg = queue.receive(timeout);

			if (msg == null) {
				throw PollingTimeoutException.standardException();
			}

			msgs.addLast(getAjaxPollingEventFromMessage(msg));

			// empty the queue. Timeout of 0 returns instantly if the queue is
			// empty
			if (queue.getQueueSize() > 0) {
				// note! that sometimes that will return null even when there
				// are messages. The client will need to re-query for the
				// remaining. This is an issue with the underlying queue.
				while ((msg = queue.receive(0)) != null) {
					msgs.addLast(getAjaxPollingEventFromMessage(msg));
				}
			}

			return msgs;
		} else {
			List<Message<?>> allMessages = queue.clear();
			for (Message<?> message : allMessages) {
				msgs.addLast(getAjaxPollingEventFromMessage(message));
			}

			return msgs;
		}
	}

	private AjaxPollingEvent<?> getAjaxPollingEventFromMessage(Message<?> msg) {

		Object payload = msg.getPayload();

		// it's either an instance of LongPollingEvent already, or..
		if (payload instanceof AjaxPollingEvent) {
			return (AjaxPollingEvent<?>) payload;
		}

		// it has a header with the event type and we build the Event.
		MessageHeaders headers = msg.getHeaders();
		String eventType = headers.get(MSG_HEADER_AJAX_POLLING_EVENT_TYPE,
				String.class);

		if (eventType == null) {
			throw new ProgrammaticException(
					"All messages on Ajax event pubsub channels must "
							+ "either be an instance of AjaxPollingEvent, or, must have the "
							+ MSG_HEADER_AJAX_POLLING_EVENT_TYPE
							+ " header set to the event type.");
		}

		return this.buildLongPollingEvent(msg);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private AjaxPollingEvent<?> buildLongPollingEvent(Message msg) {

		MessageHeaders headers = msg.getHeaders();
		String eventType = headers.get(MSG_HEADER_AJAX_POLLING_EVENT_TYPE,
				String.class);
		long time = headers.getTimestamp();

		AjaxPollingEvent env = new AjaxPollingEvent();

		env.setPayload(msg.getPayload());
		env.setEventType(eventType);
		// TODO convert this to UTC!
		env.setTimestamp(new DateTime(time));

		return env;

	}

	private void setupQueue() {

		// inputChannelName = "fxoe.longPollingEvents";

		SubscribableChannel inputChannel = (SubscribableChannel) ctx
				.getBean(inputChannelName);

		queue = new QueueChannel();
		BridgeHandler bh = new BridgeHandler();

		bh.setOutputChannel(queue);
		_bridgeConsumer = new EventDrivenConsumer(inputChannel, bh);
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

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

}
