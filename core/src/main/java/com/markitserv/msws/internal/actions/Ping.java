package com.markitserv.msws.internal.actions;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.resp.ActionResult;
import com.markitserv.msws.internal.action.resp.PingResponse;

@Service
public class Ping extends AbstractAction {
	
	Logger log = LoggerFactory.getLogger(Ping.class);

	@Override
	protected ActionResult performAction(ActionCommand cmd) {
		
		// PingResponse should return a Current TimeStamp with SuccessFailure status as success
		PingResponse response = new PingResponse(true);
		response.setCurrentTimpstamp(new DateTime());
		
		return new ActionResult(response);
	}

}
