package com.markitserv.mwws.filters;

import java.util.List;

public interface TextSearchable {
	public List<String> getSubstr();
	public void setSubstr(List<String> substr);
}
