/**
 * 
 */
package com.markitserv.hawthorne.actions;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.types.PingResponse;
import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;

/**
 * @author kiran.gogula
 *
 */
@Service
public class Ping extends AbstractAction{

	Logger log = LoggerFactory.getLogger(Ping.class);
	
	@Override
	protected ActionResult performAction(ActionParameters params,
			ActionFilters filters) {
		
		// PingResponse should return a Current TimeStamp with SuccessFailure status as success
		PingResponse response = new PingResponse(true);
		response.setCurrentTimpstamp(new DateTime());
		
		log.info("Ping Response TimeStamp: "+response.getCurrentTimpstamp());
		
		return new ActionResult(response);
	}

}
