package com.markitserv.msws.messaging;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.markitserv.msws.internal.MswsAssert;

public abstract class AbstractMswsPubSubService implements MswsPubSubService {

	Map<String, Set<MswsEventHandler>> eventHandlerRegistry = new HashMap<String, Set<MswsEventHandler>>();

	public void dispatchEvent(MswsEvent event) {

		String eventName = event.getEventName();

		@SuppressWarnings("unchecked")
		Set<MswsEventHandler> handlers = eventHandlerRegistry.get(eventName);

		MswsAssert.mswsAssert(handlers != null,
				"Could not find event handlers for event %s", eventName);

		for (MswsEventHandler handler : handlers) {
			handler.handleEvent(event);
		}
	}

	public void registerEventHandler(String eventName, MswsEventHandler handler) {

		Set<MswsEventHandler> handlers;
		if (!eventHandlerRegistry.containsKey(eventName)) {
			handlers = new HashSet<MswsEventHandler>();
		} else {
			handlers = eventHandlerRegistry.get(eventName);
		}

		handlers.add(handler);
		eventHandlerRegistry.put(eventName, handlers);

	}
}
