package com.markitserv.hawthorne.authentication;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.markitserv.msws.internal.MswsAssert;

/**
 * Authentication Token Processing Filter
 * 
 * @author swati.choudhari
 * 
 */

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

	@Autowired
	@Resource(name = "sessionRegistry")
	private SessionRegistryImpl sessionRegistry;

	public AuthenticationTokenProcessingFilter() {
		super();
	}

	Logger log = LoggerFactory
			.getLogger(AuthenticationTokenProcessingFilter.class);
	AuthenticationManager authManager;

	public AuthenticationTokenProcessingFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		@SuppressWarnings("unchecked")
		boolean isLoginRequired = false;
		boolean isUserCredentialValid = false;
		boolean isUserTokenValid = false;
		boolean isSessionExpired = false;
		HttpServletRequest htpRequest = (HttpServletRequest) request;
		HttpServletResponse htpResponse = (HttpServletResponse) response;

		String requestURI = htpRequest.getRequestURI();
		final ServletContext context = getServletContext();

		AuthenticationUtils authUtils = new AuthenticationUtils();

		// TODO move this to the spring-security.xml
		if (StringUtils.isNotBlank(htpRequest.getRequestURI())
				&& requestURI.equals("/hawthorne-server/login")) {
			isLoginRequired = true;
		}
		if (isLoginRequired) {// validate User Credentials and provide user
								// token
			String username = htpRequest.getHeader("username");
			String password = htpRequest.getHeader("psw");
			if (StringUtils.isNotBlank(username)
					&& StringUtils.isNotBlank(password)
					&& authUtils.validateUserCredentials(username, password)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						username, password);
				authentication.setDetails(new WebAuthenticationDetailsSource()
						.buildDetails(htpRequest));
				authManager = new CustomAuthunticationManager();
				SecurityContext securityContext = SecurityContextHolder
						.getContext();
				securityContext.setAuthentication(authManager
						.authenticate(authentication));
				isUserCredentialValid = true;
			}

		} else {// validate token and process the request
			String reuestedSessionId = htpRequest.getRequestedSessionId();
			System.out.println("RequestedSessionId() = "
					+ htpRequest.getRequestedSessionId());
			log.info("User Token = " + htpRequest.getRequestedSessionId());
			if (StringUtils.isNotBlank(reuestedSessionId)) {
				// check for validity of token
				MswsAssert.mswsAssert(context != null,
						"Expecting to always have a context");
				// get existing session from jsessionid
				// HttpSession session1 = (HttpSession)
				// context.getAttribute(reuestedSessionId);
				ApplicationContext ctx = WebApplicationContextUtils
						.getWebApplicationContext(context);
				sessionRegistry = (SessionRegistryImpl) ctx.getBean(
						"sessionRegistry", SessionRegistry.class);
				// sessionRegistry.getAllPrincipals();
				if (sessionRegistry != null) {
					SessionInformation sessionInfo = sessionRegistry
							.getSessionInformation(reuestedSessionId);
					if (sessionInfo != null) {
						UserDetails userDetails = (UserDetails) sessionInfo
								.getPrincipal();
						if (userDetails != null
								&& StringUtils.isNotBlank(userDetails
										.getUsername())) {
							isUserTokenValid = true;
							if (sessionInfo.isExpired()) {
								isSessionExpired = true;
							}

						}
					}
				}
			}
		}
		if ((isUserCredentialValid || isUserTokenValid) && !isSessionExpired) {
			chain.doFilter(htpRequest, htpResponse);
			log.info("User/UserToken Authenticated");
		} else if ((isUserCredentialValid || isUserTokenValid)
				&& isSessionExpired) {
			log.info("Session Expired: User Authenticated but its session is expired.");
			htpResponse
					.sendError(HttpServletResponse.SC_FORBIDDEN,
							"Session Expired: User Authenticated but its session is expired.");
		} else {
			log.info("Unauthorized: Authentication token was either missing or invalid.");
			htpResponse
					.sendError(
							HttpServletResponse.SC_UNAUTHORIZED,
							"Unauthorized: User Credentials/Authentication token was either missing or invalid.");
		}

	}

	public AuthenticationManager getAuthManager() {
		return authManager;
	}

	public void setAuthManager(AuthenticationManager authManager) {
		this.authManager = authManager;
	}

}
