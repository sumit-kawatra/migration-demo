package com.markitserv.msws.pubsub;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractMswsEventHandler implements MswsEventHandler, InitializingBean {
	
	@Autowired
	AbstractMswsPubSubService svc;

	public abstract void handleEvent(MswsEvent event);
	
	public abstract String getHandledEventName();

	@Override
	public void afterPropertiesSet() throws Exception {
		svc.registerEventHandler(this.getHandledEventName(), this);
	}
}
