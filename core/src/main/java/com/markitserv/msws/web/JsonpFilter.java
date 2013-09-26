package com.markitserv.msws.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a TEMPORARY CLASS until we're sure that the client no longer is using
 * JSONp
 * 
 * @author roy.truelove
 * 
 */
public class JsonpFilter implements Filter {

	Logger log = LoggerFactory.getLogger(this.getClass());

	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		Map<String, String[]> parms = request.getParameterMap();

		if (parms.containsKey("callback")) {
			//return;
			throw new ServletException("JSONP is no longer supported.");
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// Don't need to destroy anything
	}
}