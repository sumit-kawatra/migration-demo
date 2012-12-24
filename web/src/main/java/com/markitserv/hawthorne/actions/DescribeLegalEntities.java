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
import com.markitserv.mwws.filters.TextSearchable;
import com.markitserv.mwws.parameters.ActionParameters;
import com.markitserv.mwws.parameters.HasIdentity;
import com.markitserv.mwws.parameters.HasPagination;

@Service
/**
 * Describes all Legal Entities
 * @author roy.truelove
 *
 */
public class DescribeLegalEntities extends Action {

	public class Parameters extends ActionParameters implements HasIdentity,
			HasPagination {
	}

	public class Filters extends ActionFilters implements TextSearchable {

		private List<String> substr;

		public List<String> getSubstr() {
			return this.substr;
		}

		public void setSubstr(List<String> str) {
			this.substr = str;
		}
	}

	@Override
	protected ActionResult performAction(ActionParameters p, ActionFilters f) {
		Filters filters = (Filters) f;

		ActionResult res = new ActionResult();
		res.somestring = filters.getSubstr().get(0);
		return res;
	}
}
