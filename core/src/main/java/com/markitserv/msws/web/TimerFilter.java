package com.markitserv.msws.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class TimerFilter implements Filter {

	@Override
	public void destroy() {
		// nada
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse httpResp = (HttpServletResponse)resp;

		long startTime = System.nanoTime();
		chain.doFilter(req, resp);
		long endTime = System.nanoTime();
		
		long legnth = endTime - startTime;
		double x = legnth / 1000000.0;
		
//		httpResp.setHeader("X-Response-Time",""+x);
//		httpResp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
//		
//		resp.setContentType("sdfsdfsdf");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// nada

	}

}
