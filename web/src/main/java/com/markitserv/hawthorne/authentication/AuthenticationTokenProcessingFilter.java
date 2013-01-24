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


/**
 * Authentication Token Processing Filter
 * @author swati.choudhari
 *
 */

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {
	
	@Autowired 
	@Resource(name="sessionRegistry")
	private SessionRegistryImpl sessionRegistry;

public AuthenticationTokenProcessingFilter() {
		super();
	}
    Logger log = LoggerFactory.getLogger(AuthenticationTokenProcessingFilter.class);
	AuthenticationManager authManager;
    
    public AuthenticationTokenProcessingFilter(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        @SuppressWarnings("unchecked")
        boolean isUserValid = false;
        boolean isSessionExpired = false;
        HttpServletRequest htpRequest = (HttpServletRequest) request;
        
        HttpServletResponse htpResponse = (HttpServletResponse) response;
        
        log.info("username = "+htpRequest.getHeader("username"));
		log.info("psw ="+htpRequest.getHeader("psw"));
		
		AuthenticationUtils authUtils = new AuthenticationUtils();
              		
        String username = htpRequest.getHeader("username");
    	String password = htpRequest.getHeader("psw");
       	
        String reuestedSessionId = htpRequest.getRequestedSessionId();
       	System.out.println("htpRequest.getRequestedSessionId() = "+htpRequest.getRequestedSessionId());
		
		final ServletContext context = getServletContext();
	 	if(context != null){
	 		if(reuestedSessionId != null){
	 			//get existing session from jsessionid
	 			//HttpSession session1 = (HttpSession) context.getAttribute(reuestedSessionId);
	 			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		 		sessionRegistry = (SessionRegistryImpl) ctx.getBean("sessionRegistry", SessionRegistry.class);
	 		}
	 		
	 	}
	 	if(sessionRegistry != null){
			sessionRegistry.getAllPrincipals();
		}
		SessionInformation sessionInfo = null;
		UserDetails userDetails = null;
		UsernamePasswordAuthenticationToken authentication = null;
    	//First authenticate for jsessionid 
    	if(StringUtils.isNotBlank(reuestedSessionId) && sessionRegistry!= null) {
    				sessionInfo = sessionRegistry.getSessionInformation(reuestedSessionId);
    				if(sessionInfo != null){
    					isUserValid = true;
    					userDetails = (UserDetails) sessionInfo.getPrincipal();
    					if(userDetails != null && StringUtils.isNotBlank(userDetails.getUsername())){
    						System.out.println(sessionRegistry.getAllPrincipals());
    						System.out.println(sessionRegistry.getAllSessions(sessionInfo.getPrincipal(), true));
    						System.out.println(sessionRegistry.getAllSessions(sessionInfo.getPrincipal(), false));
    						isUserValid = true;
    						if(sessionInfo.isExpired()){
    							isSessionExpired = true;
    						}
    					
    				    }
    			    }
    	}//if not valid jsessionid then validate for user credential
    	if(!isUserValid){
            	if(username != null && username.length() >0 && password != null && password.length() >0 
            			&& authUtils.validateUserCredentials(username, password)){
            		authentication = new UsernamePasswordAuthenticationToken(username, password);
				    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(htpRequest));
				    authManager = new CustomAuthunticationManager();
				    SecurityContext securityContext = SecurityContextHolder.getContext();
				    securityContext.setAuthentication(authManager.authenticate(authentication));
				    isUserValid= true;
				    
				}
        }
    	
    	if(isUserValid && !isSessionExpired){
    		chain.doFilter(htpRequest, htpResponse);
    		log.info("User/UserToken Authenticated");
    	}else if (isUserValid && isSessionExpired){
    		log.info("Session Expired: User Authenticated but its session is expired.");
    		htpResponse.sendError( HttpServletResponse.SC_FORBIDDEN, "Session Expired: User Authenticated but its session is expired." );
    	}else {
    		log.info("Unauthorized: Authentication token was either missing or invalid.");
    		htpResponse.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication token was either missing or invalid." );
    	}
       
    }
    public AuthenticationManager getAuthManager() {
		return authManager;
	}

	public void setAuthManager(AuthenticationManager authManager) {
		this.authManager = authManager;
	}
	
}
