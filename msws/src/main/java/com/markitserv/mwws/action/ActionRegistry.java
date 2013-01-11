package com.markitserv.mwws.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.markitserv.mwws.exceptions.MwwsException;
import com.markitserv.mwws.exceptions.UnknownActionException;

/** 
 * Registry of all actions.  When an action is loaded from Spring it is expected
 * to register itself with this class
 * @author roy.truelove
 */
@Service
public class ActionRegistry {
	
	Map<String, AbstractAction> registry = new HashMap<String, AbstractAction>();
	
	public AbstractAction getActionWithName(String name) throws MwwsException {
		
		if (!registry.containsKey(name)) {
			throw UnknownActionException.standardException(name);
		}
		
		return registry.get(name);
	}
	
	public void registerAction(String name, AbstractAction action) {
		registry.put(name, action);
	}
}
