package com.markitserv.msws.actions;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.action.internal.ActionCommand;
import com.markitserv.msws.messaging.Event;
import com.markitserv.msws.messaging.JmsTemplateProvider;
import com.markitserv.msws.messaging.test.TestAsyncCommand;
import com.markitserv.msws.messaging.test.TestEvent;

@Service
public class MessagingTest extends AbstractAction {

	@Autowired
	private JmsTemplateProvider jmsTemplateProvider;

	@Override
	protected ActionResult performAction(ActionCommand command) {

		// send Event, non blocking (always)
		sendEvent();
		// send AsyncCommand, non blocking
		sendAsyncCommandNonBlocking();
		
		// send ReqResp command, blocking
		// send ReqResp command, non-blocking??

		return ActionResult.success();

	}

	private void sendEvent() {

		final TestEvent x = new TestEvent();
		x.setFoo("bar");

		jmsTemplateProvider.getPubSubTemplate().send("test.event",
				new MessageCreator() {

					@Override
					public Message createMessage(Session session)
							throws JMSException {
						// System.out.println("Message Created");
						ObjectMessage msg = session.createObjectMessage(x);
						return msg;
					}
				});
	}
	
	private void sendAsyncCommandNonBlocking() {

		final TestAsyncCommand x = new TestAsyncCommand();

		jmsTemplateProvider.getP2pTemplate().send("test.command",
				new MessageCreator() {

					@Override
					public Message createMessage(Session session)
							throws JMSException {
						// System.out.println("Message Created");
						ObjectMessage msg = session.createObjectMessage(x);
						return msg;
					}
				});
	}
}
