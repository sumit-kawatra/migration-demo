package com.markitserv.msws.svc;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 * Use for applications that have not artificial states
 * 
 * @author roy.truelove
 */
@Service(value = "artificialApplicationStateManager")
public class DefaultArtificialApplicationStateManager extends
		AbstractArtificialApplicationStateManager {

	@Override
	protected Set<String> getValidStateNames() {
		return new HashSet<String>();
	}
}
