package com.markitserv.msws.command;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.ActionCommandRunner;
import com.markitserv.msws.exceptions.MswsException;
import com.markitserv.msws.exceptions.ProgrammaticException;
import com.markitserv.msws.internal.MswsAssert;

@Service
public class CommandDispatcher implements InitializingBean {

	/** 
	 * Used to wrap async commands 
	 * @author roy.truelove
	 *
	 */
	private class RunnableDispatcher implements Runnable {
		
		private Command cmd;
		private AbstractCommandRunner runner;
		private CommandDispatcher dispatcher;
		
		public RunnableDispatcher(Command cmd, AbstractCommandRunner runner,
				CommandDispatcher dispatcher) {
			super();
			this.cmd = cmd;
			this.runner = runner;
			this.dispatcher = dispatcher;
		}

		@Override
		public void run() {
			try {
				Object retVal = runner.run(cmd);
				MswsAssert.mswsAssert(retVal == null,
						"AsyncCommand's 'run' method should always return null");
			} catch (Exception e) {
				ProgrammaticException p = new ProgrammaticException(
						"Error while running async command", e);
				dispatcher.dispatchAsyncCommand(new ErrorCommand(p));
			}
		}
	}

	@Autowired
	private CommandRunnerRegistry registry;
	private ExecutorService executor;

	public Object dispatchReqRespCommand(ReqRespCommand cmd) throws MswsException {
		AbstractCommandRunner runner = registry.getCommandRunnerForCommand(cmd);
		return runner.run(cmd);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param cmd
	 */
	public void dispatchAsyncCommand(AsyncCommand cmd) {
		
		AbstractCommandRunner runner = registry.getCommandRunnerForCommand(cmd);
		
		// NOTE - don't like this, that we have to create a new class for
		// every command.  Better way to do this?
		RunnableDispatcher asyncDispatcher = new RunnableDispatcher(cmd, runner, this);
		this.executor.execute(asyncDispatcher);

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		executor = Executors.newCachedThreadPool();
	}
}