package com.markitserv.hawthorne.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;

@Service
public class DescribeTradingRequestStatuses extends AbstractAction {

	@Autowired
	HawthorneBackend data;

	@Override
	protected ActionResult performAction(ActionParameters params,
			ActionFilters filters) {

		return new ActionResult(data.getTradingRequestStatuses());
	}
}
