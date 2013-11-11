package com.markitserv.msws.messaging;

import org.springframework.jms.core.JmsTemplate;

/**
 * You cannot use the same JMS template for pubsub as you can for p2p.
 * See http://stackoverflow.com/a/3519685/295797
 * @author roy.truelove
 *
 */
public class JmsTemplateProvider {
	
	private JmsTemplate p2pTemplate;
	private JmsTemplate pubSubTemplate;
	
	public JmsTemplate getP2pTemplate() {
		return p2pTemplate;
	}
	public void setP2pTemplate(JmsTemplate p2pTemplate) {
		this.p2pTemplate = p2pTemplate;
	}
	public JmsTemplate getPubSubTemplate() {
		return pubSubTemplate;
	}
	public void setPubSubTemplate(JmsTemplate pubSubTemplate) {
		this.pubSubTemplate = pubSubTemplate;
	}

}
