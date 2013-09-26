package com.markitserv.msws.command.internal;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.markitserv.msws.command.Command;
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
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	Map<Class<?>, AbstractCommandRunner> registry = new HashMap<Class<?>, AbstractCommandRunner>();
	
	public AbstractCommandRunner getCommandRunnerForCommand(Command cmd) throws MswsException {
		
		Class<? extends Command> clazz = cmd.getClass();
		
		if (!registry.containsKey(cmd.getClass())) {
			throw UnknownCommandException.standardException(cmd);
		}
		
		return registry.get(clazz);
	}
	
	public void registerCommandRunner(AbstractCommandRunner runner) {
		log.info("Adding a Command Runner registry for " + runner.getClass().getSimpleName());
		registry.put(runner.getCommandType(), runner);
	}
} 