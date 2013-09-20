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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

public class JsonpFilter implements Filter {

	Logger log = LoggerFactory.getLogger(this.getClass());

	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		log.info("jsonp filter");
		System.out.println("JSPON");
		
		// Since this filter always returns json P..

		@SuppressWarnings("unchecked")
		Map<String, String[]> parms = httpRequest.getParameterMap();

		// TODO make this DEBUG
		log.info("Query String: " + httpRequest.getQueryString());
		System.out.println("Query String: " + httpRequest.getQueryString());
		
		OutputStream respOutputStream = httpResponse.getOutputStream();

		GenericResponseWrapper respWrapper = new GenericResponseWrapper(
				httpResponse);
		
		byte[] respData; 
		
		if (parms.containsKey("callback")) {
			
			String callbackParam = parms.get("callback")[0];

			if (log.isDebugEnabled())
				log.debug("Wrapping response with JSONP callback '"
						+ callbackParam + "'");

			// User can override the type of the response. Only really used for
			// zenoss, which
			// doesn't like JSON.

			chain.doFilter(request, respWrapper);
			
			StringBuffer sb = new StringBuffer();

			sb.append(new String(callbackParam + "("));
			sb.append(new String(respWrapper.getData()));
			sb.append(new String(");"));

			String output = sb.toString();
			respData = output.getBytes();
			
			respWrapper.setContentType("application/javascript; charset=UTF-8");

		} else {
			log.info("No 'callback' param so not wrapping in jsonp");
			chain.doFilter(request, respWrapper);
			respData = respWrapper.getData();
			// if there's no wrapper, it's plain ol JSON
			respWrapper.setContentType("application/json; charset=UTF-8");
		}
		
		respWrapper.setContentType(mungeContentType(httpRequest, respWrapper));
		
		respOutputStream.write(respData);
		respOutputStream.close();
	}

	// Overrides content type as needed
	private String mungeContentType(HttpServletRequest req, HttpServletResponse resp) {
		
		String userAgent = req.getHeader("User-Agent");
		String method = req.getMethod();
		
		String contentType = resp.getContentType();

		boolean isIe = StringUtils.contains(userAgent, "MSIE");
		boolean isPost = method.equalsIgnoreCase("POST");
		
		if (isIe && isPost) {
			// Posts for IE require HTML type.
			contentType = ("text/html; charset=UTF-8");
		}
		
		return contentType;
	}

	public void destroy() {
	}
}
