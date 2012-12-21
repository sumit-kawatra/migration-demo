package com.markitserv.hawthorne.actions;

import org.springframework.stereotype.Service;

import com.markitserv.mwws.Action;
import com.markitserv.mwws.ActionCommand;
import com.markitserv.mwws.ActionResult;
import com.markitserv.mwws.exceptions.ProgrammaticException;

@Service
public class DescribeLegalEntities extends Action {

	@Override
	public ActionResult performAction(ActionCommand cmd) {
		throw new ProgrammaticException(
				"Got all the way here! But not implemented yet");
	}
}
