package com.markitserv.mwws.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.markitserv.mwws.exceptions.UnknownActionException;

/** 
 * Registry of all actions.  When an action is loaded from Spring it is expected
 * to register itself with this class
 * @author roy.truelove
 */
@Service
public class ActionRegistry {
	
	Map<String, Action> registry = new HashMap<String, Action>();
	
	public Action getActionWithName(String name) {
		
		if (!registry.containsKey(name)) {
			throw UnknownActionException.standardException(name);
		}
		
		return registry.get(name);
	}
	
	public void registerAction(String name, Action action) {
		registry.put(name, action);
	}
}
