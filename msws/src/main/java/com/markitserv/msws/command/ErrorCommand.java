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

	private String errorMessage;


	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
