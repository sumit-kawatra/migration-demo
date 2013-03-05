package com.markitserv.msws.command;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.markitserv.msws.exceptions.MswsException;
import com.markitserv.msws.exceptions.UnknownActionException;
import com.markitserv.msws.exceptions.UnknownCommandException;

/** 
 * Registry of all actions.  When an action is loaded from Spring it is expected
 * to register itself with this class
 * @author roy.truelove
 */
@Service
public class CommandRunnerRegistry {
	
	Map<Class<?>, AbstractCommandRunner> registry = new HashMap<Class<?>, AbstractCommandRunner>();
	
	public AbstractCommandRunner getCommandRunnerForCommand(Command cmd) throws MswsException {
		
		Class<? extends Command> clazz = cmd.getClass();
		
		if (!registry.containsKey(cmd.getClass())) {
			throw UnknownCommandException.standardException(cmd);
		}
		
		return registry.get(clazz);
	}
	
	public void registerCommandRunner(AbstractCommandRunner runner) {
		registry.put(runner.getCommandType(), runner);
	}
} 