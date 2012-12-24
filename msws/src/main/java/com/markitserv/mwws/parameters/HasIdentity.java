package com.markitserv.mwws.parameters;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds support for identity parameters for an action
 * @author roy.truelove
 *
 */
public interface HasIdentity {
	/**
	 * Set of id parameters for this action.
	 */
	public Set<String> Id = new HashSet<String>();
}
