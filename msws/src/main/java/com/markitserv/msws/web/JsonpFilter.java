package com.markitserv.msws.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

public class JsonpFilter implements Filter {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void init(FilterConfig fConfig) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		log.debug("jsonp filter");

		@SuppressWarnings("unchecked")
		Map<String, String[]> parms = httpRequest.getParameterMap();

		if(parms.containsKey("callback")) {
			if(log.isDebugEnabled())
				log.debug("Wrapping response with JSONP callback '" + parms.get("callback")[0] + "'");

			OutputStream out = httpResponse.getOutputStream();

			GenericResponseWrapper wrapper = new GenericResponseWrapper(httpResponse);

			chain.doFilter(request, wrapper);
			
			StringBuffer sb = new StringBuffer();

			sb.append(new String(parms.get("callback")[0] + "("));
			sb.append(new String(wrapper.getData()));
			sb.append(new String(");"));
			
			String output = sb.toString();
			out.write(output.getBytes());
			
			log.debug("Server resp : " + output);

			wrapper.setContentType("text/javascript;charset=UTF-8");
			
			out.close();
		} else {
			log.debug("No 'callback' param so not wrapping in jsonp");
			chain.doFilter(request, response);
		}
	}

	public void destroy() {}
}
