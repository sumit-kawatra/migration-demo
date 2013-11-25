package com.markitserv.msws.messaging;

/**
 * Everything that throws an event should implement this interface. Nearly
 * always, these will push out to some kind of pubsub messaging.
 * 
 * @author roy.truelove
 * 
 * @param <T>
 */
public interface Notifier<T> {

	public void notify(T payload);

}
