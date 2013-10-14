package com.markitserv.msws;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.markitserv.msws.exceptions.InvalidActionParamValueException;

public abstract class AbstractArtificialApplicationStateManager implements
		InitializingBean {

	private Map<String, Boolean> states = new HashMap<String, Boolean>();
	private Set<String> stateNames;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// If the key isn't set, it's false by default
	public boolean isInState(Object key) {
		
		boolean state = false;

		if (states.containsKey(key)) {
			return state = states.get(key);
		}
		
		if (state) {
			log.warn("Operating under artificial state '" + key + "'");
		}
		
		return state;
	}

	public void setState(String key, Boolean value) {
		
		if (!stateNames.contains(key)) {
			throw new InvalidActionParamValueException("State '" + key + "' is not valid.");
		}
		
		states.put(key, value);
	}
	
	public void clearAll() {
		states = new HashMap<String, Boolean>();
	}

	protected abstract Set<String> getValidStateNames();

	@Override
	public void afterPropertiesSet() throws Exception {
		stateNames = this.getValidStateNames();
		clearAll();
	}
}
