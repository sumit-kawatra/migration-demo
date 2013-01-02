package com.markitserv.mwws.action;

import com.markitserv.mwws.command.ReqRespCommand;

public class ActionCommand implements ReqRespCommand {
	
	private static final long serialVersionUID = 1L;
	private String action;
	private ActionParameters params;
	private ActionFilters filters;
	
	/**
	 * @param action
	 * @param params
	 */
	public ActionCommand(String action, ActionParameters params, ActionFilters filters) {
		
		super();
		
		this.action = action;
		this.params = params;
		this.filters = filters;
	}

	public ActionCommand() {
		super();
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public ActionParameters getParameters() {
		return params;
	}

	public void setParameters(ActionParameters params) {
		this.params = params;
	}

	public ActionFilters getFilters() {
		return filters;
	}

	public void setFilters(ActionFilters filters) {
		this.filters = filters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((filters == null) ? 0 : filters.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionCommand other = (ActionCommand) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (filters == null) {
			if (other.filters != null)
				return false;
		} else if (!filters.equals(other.filters))
			return false;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		return true;
	}
} 