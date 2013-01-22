/**
 * 
 */
package com.markitserv.mwws.emailCommand;

import com.markitserv.mwws.exceptions.MwwsException;

/**
 * @author kiran.gogula
 *
 */
public abstract class AbstractEmailCommandRunner {	
	
	protected abstract Object sendMail(EmailCommand cmd) throws MwwsException;

}
