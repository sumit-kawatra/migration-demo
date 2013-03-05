/**
 * 
 */
package com.markitserv.msws.command;

/**
 * @author kiran.gogula
 *
 */
public class ErrorCommand implements AsyncCommand {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	private Throwable error;
	
	public ErrorCommand(Throwable error) {
		super();
		this.error = error;
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable errorMessage) {
		this.error = errorMessage;
	}
}
