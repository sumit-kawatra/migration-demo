package com.markitserv.hawthorne.authentication;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import com.markitserv.hawthorne.actions.DescribeLegalEntities;



public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

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
        HttpServletRequest htpRequest = (HttpServletRequest) request;
        HttpServletResponse htpResponse = (HttpServletResponse) response;
        log.info("username = "+htpRequest.getHeader("username"));
		log.info("psw ="+htpRequest.getHeader("psw"));
		AuthenticationUtils authUtils = new AuthenticationUtils();
		HttpSession session = ((HttpServletRequest) request).getSession();
        ArrayList<HawthornLoginUser> authenticatedUserList =  new ArrayList<HawthornLoginUser>();
        		if(session.getAttribute("authenticatedUserList") != null){
			authenticatedUserList = ((ArrayList<HawthornLoginUser>) session.getAttribute("authenticatedUserList"));
		}
        String username = htpRequest.getHeader("username");
    	String password = htpRequest.getHeader("psw");
    	String usertoken = htpRequest.getHeader("usertoken");
    	ArrayList<HawthornLoginUser> authenticatedUserListNew =  new ArrayList<HawthornLoginUser>();
    	if(StringUtils.isNotBlank(usertoken) && authUtils.validateToken(usertoken, authenticatedUserList)) {
    				isUserValid = true;
    				String newToken = "";
    				HawthornLoginUser userLogin = authUtils.getUserFromToken(usertoken,authenticatedUserList);
    				UsernamePasswordAuthenticationToken authentication = 
				            new UsernamePasswordAuthenticationToken(userLogin.getUserName(), userLogin.getPassword());
				    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
				    authManager = new CustomAuthunticationManager();
					SecurityContextHolder.getContext().setAuthentication(authManager.authenticate(authentication));
    				authenticatedUserListNew = authUtils.UpdateTokenAndLoggedInTime(usertoken, authenticatedUserList);
    				session.setAttribute("authenticatedUserList", authenticatedUserListNew);
    				for(HawthornLoginUser user : authenticatedUserListNew){
    					if(user.getUserName().equals(userLogin.getUserName())){
    						newToken=user.getUserToken();
    					}
    				}
    				Cookie userCookie = new Cookie("userToken", newToken);
					htpResponse.addCookie(userCookie);
    	}else{
            	if(username != null && username.length() >0 && password != null && password.length() >0 && authUtils.validateUserCredentials(username,password)){
            		isUserValid = true;
		
            		
            		UsernamePasswordAuthenticationToken authentication = 
				            new UsernamePasswordAuthenticationToken(username, password);
				    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
				    authManager = new CustomAuthunticationManager();
					SecurityContextHolder.getContext().setAuthentication(authManager.authenticate(authentication));
					
					HawthornLoginUser userLogin = new HawthornLoginUser();
					userLogin.setUserName(username);
					userLogin.setPassword(password);
					userLogin.setLoggedInTime(System.currentTimeMillis());
					String newToken = username+userLogin.getLoggedInTime();
					userLogin.setUserToken(newToken);
					authenticatedUserList.add(userLogin);
					((HttpServletRequest) request).getSession().setAttribute("authenticatedUserList", authenticatedUserList);
					Cookie userCookie = new Cookie("userToken", newToken);
					htpResponse.addCookie(userCookie);
					
            	} 
        }
    	if(isUserValid){
    		chain.doFilter(request, response);
    		log.info(username + "User Authenticated");
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
	
//	public boolean validateToken(String token, ArrayList<HawthornLoginUser> authenticatedUserList){
//		boolean isTokenValid = false;
//		if(authenticatedUserList.size() >0){
//		for(HawthornLoginUser user : authenticatedUserList){
//			//check whether token exists
//			if(user.getUserToken().equals(token)){
//				//check for token expiry
//				long sessionTimeOutValue = 60 * 1000; // convert seconds to miliseconds (60 sec)
//				long lastActivityTime = user.getLoggedInTime();
//				long currentTimeStamp = System.currentTimeMillis();
//				if(currentTimeStamp - lastActivityTime < sessionTimeOutValue){
//					isTokenValid = true;
//				} 
//			}
//		}	
//		}
//		return isTokenValid;
//	}
//	
//	public boolean validateUserCredentials(String username, String password){
//		boolean isValid = false;
//		if(username.equals("user1") && password.equals("abc")){
//			isValid = true;
//		}else if(username.equals("user2") && password.equals("xyz")){
//			isValid = true;
//		} 
//		return isValid;
//	}
//	
//	public HawthornLoginUser getUserFromToken (String token, ArrayList<HawthornLoginUser> authenticatedUserList){
//		HawthornLoginUser usr = new HawthornLoginUser();
//		
//		for(HawthornLoginUser user : authenticatedUserList){
//			//check whether token exists
//			if(user.getUserToken().equals(token)){
//				//check for token expiry
//				long sessionTimeOutValue = 60 * 1000; // convert seconds to miliseconds (60 sec)
//				long lastActivityTime = user.getLoggedInTime();
//				long currentTimeStamp = System.currentTimeMillis();
//				if(currentTimeStamp - lastActivityTime < sessionTimeOutValue){
//					return user;
//				} 
//			}
//		}
//		return usr;
//	}
//	
//	public ArrayList<HawthornLoginUser> UpdateTokenAndLoggedInTime (String token, ArrayList<HawthornLoginUser> authenticatedUserList){
//		ArrayList<HawthornLoginUser> authenticatedUserListNew = new ArrayList<HawthornLoginUser>();
//		for(HawthornLoginUser user : authenticatedUserList){
//			//check whether token exists
//			if(user.getUserToken().equals(token)){
//				//update time and token
//				long currentTimeStamp = System.currentTimeMillis();
//				user.setLoggedInTime(currentTimeStamp);
//				user.setUserToken("userToken"+currentTimeStamp);	
//		    }
//			authenticatedUserListNew.add(user);
//	    }
//		return authenticatedUserListNew;
//	}
	
}
