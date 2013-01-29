package com.markitserv.hawthorne.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.BuildInformation;
import com.markitserv.hawthorne.types.Build;
import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;

@Service
public class DescribeBuildInformation extends AbstractAction {
	
	@Autowired
	BuildInformation buildInfo;

	@Override
	protected ActionResult performAction(ActionParameters params,
			ActionFilters filters) {
		
		return new ActionResult(new Build(buildInfo.getBuild()));
	}
}
