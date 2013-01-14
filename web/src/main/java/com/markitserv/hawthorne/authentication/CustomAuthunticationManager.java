package com.markitserv.hawthorne.authentication;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class CustomAuthunticationManager implements AuthenticationManager {
@Autowired
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if(StringUtils.isBlank((String) authentication.getPrincipal()) || StringUtils.isBlank((String) authentication.getCredentials())){
			throw new BadCredentialsException("Invalid username/password");
	}


	User user = null;
    List<MyGrantedAuthority> grantedAuthorities = new ArrayList<MyGrantedAuthority>();
    grantedAuthorities.add(new MyGrantedAuthority());
	try{
	  user =  new User("user1", "abc", grantedAuthorities);
	
	}
	catch(Exception e){
		throw new AuthenticationServiceException("Currently we are unable to process your request. Kindly try again later.");
	}

  return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), grantedAuthorities);
}
}

class MyGrantedAuthority implements GrantedAuthority{
	
	@Override
	public String getAuthority() {
		return "ROLE_USER";
	}
}