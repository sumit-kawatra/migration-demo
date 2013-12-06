package com.markitserv.msws.internal.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.resp.ActionResult;
import com.markitserv.msws.beans.SessionInfo;
import com.markitserv.msws.request.MswsRequestContextHolder;

@Service
public class DescribeSession extends AbstractAction {

	@Autowired
	private MswsRequestContextHolder ctxHolder;

	@Override
	protected ActionResult performAction(ActionCommand command) {

		SessionInfo session = ctxHolder.getRequestContext().getSessionInfo();

		return new ActionResult(session);
	}

	@Override
	public String getDescription() {
		return "Describes the session of the currently logged in user.";
	}

}
