package com.markitserv.msws.internal.filters;

import java.util.List;

import com.markitserv.msws.beans.AjaxPollingEvent;
import com.markitserv.msws.filters.AbstractFilter;

/**
 * Filters AjaxPollingEvents by type.
 * 
 * @author roy.truelove
 * 
 */
public class AjaxEventTypeFilter extends AbstractFilter<AjaxPollingEvent<?>> {

	List<String> validEventTypes;

	private AjaxEventTypeFilter(List<String> validEventTypes) {
		super();
		this.validEventTypes = validEventTypes;
	}

	@Override
	protected boolean shouldBeFilteredOut(AjaxPollingEvent<?> item) {

		String type = item.getEventType();

		if (validEventTypes.contains(type)) {
			return false;
		} else {
			return true;
		}
	}

	public static List<AjaxPollingEvent<?>> filter(
			List<AjaxPollingEvent<?>> toFilter, List<String> validEventTypes) {

		AjaxEventTypeFilter f = new AjaxEventTypeFilter(validEventTypes);
		return f.filter(toFilter);
	}
}
