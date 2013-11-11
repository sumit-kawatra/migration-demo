package com.markitserv.msws.command.internal;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.exceptions.MswsException;
import com.markitserv.msws.messaging.Command;


/**
 * All CommandRunners should subclass this class
 * @author roy.truelove
 *
 */
@Service
public abstract class AbstractCommandRunner implements InitializingBean {
	
	@Autowired
	CommandRunnerRegistry registry;

	/**
	 * Runs the command.  Async commands should return null.
	 * @param cmd The command to run
	 * @return The result of the command.  Should return null if async
	 */
	public abstract Object run(Command cmd);
	
	/**
	 * Returns the type of Command that this runner handles
	 * @return
	 */
	public abstract Class<?> getCommandType();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		registry.registerCommandRunner(this);
	}
} 