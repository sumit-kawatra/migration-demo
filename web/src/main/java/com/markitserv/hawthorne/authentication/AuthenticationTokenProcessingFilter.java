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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
 * @author swati.choudhari
 * 
 */

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {
    
	@Autowired
	@Resource(name = "sessionRegistry")
	private SessionRegistryImpl sessionRegistry;
	
	// TODO why isn't this loaded from Spring?  Comes back as null
	//@Autowired
	//@Qualifier("authenticationManager")
	private AuthenticationManager authManager;
    
	public static final String HAWTHORNE_USER_USERNAME = "username";
	public static final String HAWTHORNE_USER_PASSWORD = "password";
	public static final String HAWTHORNE_LOGIN = "/login";
	Logger log = LoggerFactory.getLogger(AuthenticationTokenProcessingFilter.class);

	public AuthenticationTokenProcessingFilter() {
		super();
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
		String LoginReqURI = htpRequest.getContextPath()+HAWTHORNE_LOGIN;
		String requestURI = htpRequest.getRequestURI();
		final ServletContext context = getServletContext();

		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		sessionRegistry = (SessionRegistryImpl) ctx.getBean("sessionRegistry", SessionRegistry.class);
		
		if (StringUtils.isNotBlank(htpRequest.getRequestURI()) && requestURI.equals(LoginReqURI)) {
			isLoginRequired = true;
		}
		if (isLoginRequired) {// validate User Credentials and provide user token
			String username = htpRequest.getHeader(HAWTHORNE_USER_USERNAME);
			String password = htpRequest.getHeader(HAWTHORNE_USER_PASSWORD);
			SecurityContext securityContext = SecurityContextHolder.getContext();
			
			if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(htpRequest));
				
				//NOTE see above - have to get it from the ctx because it's not being autowired
				AuthenticationManager authManager = (AuthenticationManager) ctx.getBean("authenticationManager");
				try{
					Authentication auth= authManager.authenticate(authentication);
					if(auth.isAuthenticated()){
						isUserCredentialValid = true;
						securityContext.setAuthentication(auth);
						System.out.println(htpRequest.getSession());
						HttpSession session = htpRequest.getSession();
						//invalidate the previous sessions if any
						if(!session.isNew()){
							session.invalidate();
						}
					}
				}catch(BadCredentialsException e){
					isUserCredentialValid = false;
				}
			}

		} else {// validate token and process the request
			String reuestedSessionId = htpRequest.getRequestedSessionId();
			System.out.println("RequestedSessionId() = "+ htpRequest.getRequestedSessionId());
			log.info("User Token = " + htpRequest.getRequestedSessionId());
			if (StringUtils.isNotBlank(reuestedSessionId)) {
				// check for validity of token
				MswsAssert.mswsAssert(context != null,"Expecting to always have a context");
				// sessionRegistry.getAllPrincipals();
				if (sessionRegistry != null) {
					SessionInformation sessionInfo = sessionRegistry.getSessionInformation(reuestedSessionId);
					if (sessionInfo != null) {
						UserDetails userDetails = (UserDetails) sessionInfo.getPrincipal();
						if (userDetails != null && StringUtils.isNotBlank(userDetails.getUsername())) {
							isUserTokenValid = true;
							if (sessionInfo.isExpired()) {
								isSessionExpired = true;
							}
						}
					}
				}
			}
		}
		//Valid credentials/token && session not expired.
		if ( (isUserCredentialValid || isUserTokenValid) && !isSessionExpired) {
			chain.doFilter(htpRequest, htpResponse);
			log.info("User/token Authenticated successfully");
		//valid credentials/token but session expired.
		} else if ((isUserCredentialValid || isUserTokenValid) && isSessionExpired) {
			log.info("Session Expired: User Authenticated but its session is expired.");
			htpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,"Session Expired: User Authenticated but its session is expired.");
		} else {
			log.info("Unauthorized: Authentication token was either missing or invalid.");
			htpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized: User Credentials/Authentication token was either missing or invalid.");
		}
	}

	
}
