/**
 * 
 */
package com.markitserv.msws.command;

import static com.markitserv.msws.internal.MswsAssert.mswsAssert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.markitserv.msws.exceptions.MswsException;

/**
 * @author kiran.gogula
 * 
 */
public class ErrorCommandRunner extends AbstractCommandRunner {

	Logger log = LoggerFactory.getLogger(ErrorCommandRunner.class);

	private String env;
	
	private String errorDistributionGroup;
	
	@Autowired
	private CommandDispatcher dispatcher; 
	
	@Override
	public Object run(Command cmd) throws MswsException {
		mswsAssert(cmd!=null, "ErrorCommand must be set");
		ErrorCommand errorCommand = (ErrorCommand) cmd;
		EmailCommand emailCommand = new EmailCommand();		 
		 emailCommand.setSubject(getEnv() +"  "+ errorCommand.getErrorMessage());
		 emailCommand.setBody(errorCommand.getErrorMessage());
		 emailCommand.setTo(getErrorDistributionGroup());
		 // Sending the mail to error distribution group
		 dispatcher.dispatchAsyncCommand(emailCommand);
		return null;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getErrorDistributionGroup() {
		return errorDistributionGroup;
	}

	public void setErrorDistributionGroup(String errorDistributionGroup) {
		this.errorDistributionGroup = errorDistributionGroup;
	}

	public CommandDispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(CommandDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	
	
	

}
