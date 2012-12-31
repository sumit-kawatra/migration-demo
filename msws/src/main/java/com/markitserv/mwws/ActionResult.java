package com.markitserv.mwws;

import java.util.Set;

import javax.validation.constraints.Size;

public class ActionResult {
	
	@Size(max=3)
	public Set<String> somestring;

}
