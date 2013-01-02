package com.markitserv.hawthorne.actions;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.util.HardcodedData;
import com.markitserv.mwws.action.Action;
import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.action.ActionFilters;
import com.markitserv.mwws.action.ActionParameters;
import com.markitserv.mwws.action.ActionResult;
import com.markitserv.mwws.exceptions.ProgrammaticException;

@Service
/**
 * Describes all Legal Entities
 * @author roy.truelove
 *
 */
public class DescribeLegalEntities extends Action {
	
	@Autowired
	private HardcodedData data;

	@Override
	protected ActionResult performAction(ActionParameters p, ActionFilters f) {
		
		ActionResult res = new ActionResult();
		res.setCollection(data.getLegalEntities());
		return res;
	}
}
