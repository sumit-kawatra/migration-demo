package com.markitserv.msws.messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.core.SubscribableChannel;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.handler.BridgeHandler;

/**
 * When starting up, ajax queues have no subscribers until a session is created
 * that subscribes to them. Spring Integration doesn't like this, so this
 * initializer will give them a single subscribed to a null channel. I hate
 * this, open to ways around it.
 * 
 * @author roy.truelove
 * 
 */
public class AjaxPollingQueueInitializer implements InitializingBean,
		ApplicationContextAware, DisposableBean {

	private Set<String> queues;
	private List<EventDrivenConsumer> consumers = new ArrayList<EventDrivenConsumer>();
	private ApplicationContext ctx;

	public Set<String> getQueues() {
		return queues;
	}

	public void setQueues(Set<String> queues) {
		this.queues = queues;
	}

	private void initQueues() {

		for (String queueName : queues) {

			SubscribableChannel inputChannel = (SubscribableChannel) ctx
					.getBean(queueName);

			NullChannel queue = new NullChannel();
			BridgeHandler bh = new BridgeHandler();

			bh.setOutputChannel(queue);
			EventDrivenConsumer consumer = new EventDrivenConsumer(inputChannel, bh);
			consumer.start();
			consumers.add(consumer);
		}
	}

	private void tearDownQueues() {

		for (EventDrivenConsumer c : consumers) {
			c.stop();
		}
	}

	@Override
	public void destroy() throws Exception {
		tearDownQueues();

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initQueues();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {

		this.ctx = applicationContext;

	}

}
