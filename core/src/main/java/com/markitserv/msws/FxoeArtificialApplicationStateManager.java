package com.markitserv.msws;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class FxoeArtificialApplicationStateManager extends
		AbstractArtificialApplicationStateManager {

	@Override
	protected Set<String> getValidStateNames() {
		// There are none yet
		return new HashSet<String>();
	}
}
