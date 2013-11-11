package com.markitserv.msws.messaging;

public interface MswsPubSubService {
	
	public abstract void send(MswsEvent event);

}
