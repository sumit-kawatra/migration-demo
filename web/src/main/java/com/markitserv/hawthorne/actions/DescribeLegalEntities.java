package com.markitserv.hawthorne.actions;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.actions.DescribeLegalEntities.Filters;
import com.markitserv.mwws.Action;
import com.markitserv.mwws.ActionCommand;
import com.markitserv.mwws.ActionResult;
import com.markitserv.mwws.exceptions.ProgrammaticException;
import com.markitserv.mwws.filters.ActionFilters;
import com.markitserv.mwws.parameters.ActionParameters;

@Service
/**
 * Describes all Legal Entities
 * @author roy.truelove
 *
 */
public class DescribeLegalEntities extends Action {

	public class Parameters extends ActionParameters {
		public String id;
		public int pageNumber;
		public int pageSize;
	}

	public class Filters extends ActionFilters {
		
		private List<String> substr;

		public List<String> getSubstr() {
			return substr;
		}

		public void setSubstr(List<String> substr) {
			this.substr = substr;
		}
	}

	@Override
	protected ActionResult performAction(ActionParameters p, ActionFilters f) {
		Filters filters = (Filters) f;

		ActionResult res = new ActionResult();
		res.somestring = filters.substr.get(0);
		return res;
	}
}
