package com.markitserv.hawthorne.authentication;

import java.util.ArrayList;

public class AuthenticationUtils {
	
	public boolean validateToken(String token, ArrayList<HawthornLoginUser> authenticatedUserList){
		boolean isTokenValid = false;
		if(authenticatedUserList.size() >0){
		for(HawthornLoginUser user : authenticatedUserList){
			//check whether token exists
			if(user.getUserToken().equals(token)){
				//check for token expiry
				long sessionTimeOutValue = 60 * 1000; // convert seconds to miliseconds (60 sec)
				long lastActivityTime = user.getLoggedInTime();
				long currentTimeStamp = System.currentTimeMillis();
				if(currentTimeStamp - lastActivityTime < sessionTimeOutValue){
					isTokenValid = true;
				} 
			}
		}	
		}
		return isTokenValid;
	}
	
	public boolean validateUserCredentials(String username, String password){
		boolean isValid = false;
		if(username.equals("user1") && password.equals("abc")){
			isValid = true;
		}else if(username.equals("user2") && password.equals("xyz")){
			isValid = true;
		} 
		return isValid;
	}
	
	public HawthornLoginUser getUserFromToken (String token, ArrayList<HawthornLoginUser> authenticatedUserList){
		HawthornLoginUser usr = new HawthornLoginUser();
		
		for(HawthornLoginUser user : authenticatedUserList){
			//check whether token exists
			if(user.getUserToken().equals(token)){
				//check for token expiry
				long sessionTimeOutValue = 60 * 1000; // convert seconds to miliseconds (60 sec)
				long lastActivityTime = user.getLoggedInTime();
				long currentTimeStamp = System.currentTimeMillis();
				if(currentTimeStamp - lastActivityTime < sessionTimeOutValue){
					return user;
				} 
			}
		}
		return usr;
	}
	
	public ArrayList<HawthornLoginUser> UpdateTokenAndLoggedInTime (String token, ArrayList<HawthornLoginUser> authenticatedUserList){
		ArrayList<HawthornLoginUser> authenticatedUserListNew = new ArrayList<HawthornLoginUser>();
		for(HawthornLoginUser user : authenticatedUserList){
			//check whether token exists
			if(user.getUserToken().equals(token)){
				//update time and token
				long currentTimeStamp = System.currentTimeMillis();
				user.setLoggedInTime(currentTimeStamp);
				user.setUserToken("userToken"+currentTimeStamp);	
		    }
			authenticatedUserListNew.add(user);
	    }
		return authenticatedUserListNew;
	}
}
