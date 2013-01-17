package com.markitserv.hawthorne.authentication;


import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.markitserv.hawthorne.actions.DescribeLegalEntities;

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
    Logger log = LoggerFactory.getLogger(DescribeLegalEntities.class);
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
		log.info("jsessionid ="+htpRequest.getHeader("jsessionid"));
		AuthenticationUtils authUtils = new AuthenticationUtils();
		HttpSession session = htpRequest.getSession();
		System.out.println("new session id = "+session.getId());
               		
        String username = htpRequest.getHeader("username");
    	String password = htpRequest.getHeader("psw");
       	String jsessionid = htpRequest.getHeader("jsessionid");
    	
    	System.out.println(session.getCreationTime());
		System.out.println(session.getLastAccessedTime());
		System.out.println(session.getMaxInactiveInterval());
		System.out.println(session.getSessionContext().getSession(jsessionid));
		

		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		SessionRegistry sessionRegistry = ctx.getBean("sessionRegistry", SessionRegistry.class);
		System.out.println("sessionRegistry = "+sessionRegistry);
		if(sessionRegistry != null){
			sessionRegistry.getAllPrincipals();
		}
		SessionInformation sessionInfo = null;
		UserDetails userDetails = null;
		UsernamePasswordAuthenticationToken authentication = null;
    	//First authenticate for jsessionid 
    	if(StringUtils.isNotBlank(jsessionid) && sessionRegistry!= null) {
    				
    				sessionInfo = sessionRegistry.getSessionInformation(jsessionid);
    				if(sessionInfo != null){
    					isUserValid = true;
    					userDetails = (UserDetails) sessionInfo.getPrincipal();
    					if(userDetails != null && StringUtils.isNotBlank(userDetails.getUsername())){
//    						System.out.println(sessionRegistry.getAllPrincipals());
//    						System.out.println(sessionRegistry.getAllSessions(sessionInfo.getPrincipal(), true));
//    						System.out.println(sessionRegistry.getAllSessions(sessionInfo.getPrincipal(), false));
    						authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
    						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(htpRequest));
    						authManager = new CustomAuthunticationManager();
    					    SecurityContextHolder.getContext().setAuthentication(authManager.authenticate(authentication));
    					    isUserValid = true;
    					}
    					
    				}else {
    					// handle session expiry
    				}
    	}//if not valid jsessionid then validate for user credential
    	if(!isUserValid){
            	if(username != null && username.length() >0 && password != null && password.length() >0 && authUtils.validateUserCredentials(username, password)){
            		
            		authentication = new UsernamePasswordAuthenticationToken(username, password);
				    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(htpRequest));
				    authManager = new CustomAuthunticationManager();
					SecurityContextHolder.getContext().setAuthentication(authManager.authenticate(authentication));
					isUserValid = true;
				}
        }
    	if(isUserValid){
    		chain.doFilter(request, response);
    		log.info("User "+ username + " Authenticated");
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
