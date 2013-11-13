package com.markitserv.msws.oneoffs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.core.SubscribableChannel;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.support.channel.BeanFactoryChannelResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.markitserv.msws.oneoffs.etc.integration.CommandA;
import com.markitserv.msws.oneoffs.etc.integration.CommandB;
import com.markitserv.msws.oneoffs.etc.integration.EventA;
import com.markitserv.msws.oneoffs.etc.integration.StringProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
	@ContextConfiguration("classpath:spring/spring-integration-text.xml")
})
public class SpringIntegrationLearningTest implements ApplicationContextAware {
	
	private ApplicationContext ctx;
	private Logger log = LoggerFactory.getLogger(SpringIntegrationLearningTest.class);
	private MessagingTemplate template;
	
	@Autowired
	private StringProcessor sp;
	
	@Before
	public void setup() {
		template = new MessagingTemplate();
		template.setChannelResolver(new BeanFactoryChannelResolver(ctx));
	}

	// ************************************************** Events
	
	@Test
	public void sendEventUsingExplictMessageWithServiceActivator() {
		
		// Should log from EventHandlerA1 and A2
		Message<EventA> msg = MessageBuilder.withPayload(new EventA()).build();
		template.send("events", msg);
		
	}
	
	@Test
	public void sendEventUsingExplictMessageWithSubscribe() {
		
		SubscribableChannel channel = (SubscribableChannel) this.getChannel("events");
		
		LoggingHandler lh = new LoggingHandler("ERROR");
		
		channel.subscribe(lh);
		
		EventA evt = new EventA();
		
		Message<EventA> msg = MessageBuilder.withPayload(evt).build();
		
		// Should log from EventHandlerA1 and A2, and, the logging handler above
		template.send("events", msg);
	}
	
	@Test
	public void sendEventUsingNonExplictMessageServiceProvider() {
		fail();
	}
	
	// ************************************************** Commands
	
	@Test
	public void sendNoRespCommandOnQueueUsingExplictMessageWithServiceActivator() {
		
		Message<CommandA> msg = MessageBuilder.withPayload(new CommandA()).build();
		
		// Should log command runners A1 and A2 round robin
		for (int x = 1; x <= 10; x++) {
			template.send("commands", msg);
		}
	}
	
	@Test
	public void sendReqRespCommandOnQueueUsingExplictMessageWithServiceActivator() {
		
		CommandB cmd = new CommandB();
		
		Message<CommandB> msg = MessageBuilder.withPayload(cmd).build();
		Message<String> resp = (Message<String>) template.sendAndReceive("commandsReqResp", msg);
		
		log.error("Got response : " + resp.getPayload());
	}
	
	@Test
	public void sendSyncReqRespCommandOnPubSubUsingExplictMessageWithServiceActivator() {
		
		fail();
		
	}
	
	@Test
	public void sendAsyncReqRespCommandOnQueueUsingExplictMessageWithServiceActivator() {
		
		
		
	}
	
	@Test
	public void sendReqRespCommandOnPubSubUsingExplictMessageWithSubscribe() {
		
		fail();
		
	}
	
	
	@Test
	public void sendNoRespCommandOnQueueUsingNonExplictMessageWithServiceActivator() {
		fail();
	}
	
	// ************************************************** Full Flows
	
	@Test
	public void startingGateway() throws InterruptedException {
		
		for (int x = 1; x<=100; x++) {
			String res = sp.processInt(x);
			log.error("Got from Gateway: " + res);
		}
		
		log.error("DONE");
		
		Thread.sleep(1000);
	}
	
	private MessageChannel getChannel(String name) {
		return this.ctx.getBean(name, MessageChannel.class);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		
		this.ctx = applicationContext;
		
	}
}
