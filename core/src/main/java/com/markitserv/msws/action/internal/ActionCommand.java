package com.markitserv.msws.action.internal;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.command.BlockingCommand;
import com.markitserv.msws.internal.MswsAssert;
import com.markitserv.msws.types.SessionInfo;

public class ActionCommand extends BlockingCommand {
	
	private static final long serialVersionUID = 1L;
	private String action;
	private ActionParameters params;
	private ActionFilters filters;
	private SessionInfo sessionInfo;
	
	public ActionCommand() {
		super();
	}
	
	/**
	 * @param action
	 * @param params
	 */
	public ActionCommand(String action, ActionParameters params, ActionFilters filters) {
		
		super();
		
		this.action = action;
		this.params = params;
		this.filters = filters;
		
		MswsAssert.mswsAssert(!StringUtils.isBlank(action), "Action must be set.");
		MswsAssert.mswsAssert(params != null, "Params must be set.");
		MswsAssert.mswsAssert(filters != null, "Filters must be set.");
	}

//	public ActionCommand() {
//		super();
//	}

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
	
	public void addParameter(String key, Object value) {
		params.addParameter(key, value);
	}
	
	public void addParameters(Map<String, Object> newParams) {
		params.addParameters(newParams);
	}
	
	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}


	@Override
	public int hashCode() {
		   return HashCodeBuilder.reflectionHashCode(this);
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
		return EqualsBuilder.reflectionEquals(other, obj);		
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
		ToStringStyle.MULTI_LINE_STYLE);
	}

	
} 