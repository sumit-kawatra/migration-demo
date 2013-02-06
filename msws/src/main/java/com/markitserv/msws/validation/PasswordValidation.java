package com.markitserv.msws.validation;

import java.util.Map;

/**
 * PasswordValidation
 * @author swati.choudhari
 *
 */
public class PasswordValidation extends AbstractValidation {
	
	public static final int MIN_PASSWORD_LENGTH = 8;
	public static final int MAX_PASSWORD_LENGTH = 16;
	//TODO - will be used if required in future
    //public static final String SPECIAL_CHARACTERS = "!@#$%^&*()~`-=_+[]{}|:\";',./<>?";

	@Override
	public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> map) {
		String password = target.toString().trim();
		boolean containsAtLeastOneUpperCase =  false;
		boolean containsAtLeastOneLowerCase = false;
		boolean containsAtLeastOneDigit = false;
		//boolean containsAtLeastOneSpecialCharecter = false;
		if(password.length()< MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH){
			return ValidationResponse.createInvalidResponse("Password sould have min 8 characters and max 16.");
		}
		char[] passwordCharecters = password.toCharArray();
		
		for(char pswChar : passwordCharecters) {
	    	if (Character.isWhitespace(pswChar)){
				return ValidationResponse
						.createInvalidResponse("Invalid password format. Password should not contain any white spaces in between.");
	    	} else if (Character.isUpperCase(pswChar)) {
	    		containsAtLeastOneUpperCase = true;
	        } else if (Character.isLowerCase(pswChar)) {
	        	containsAtLeastOneLowerCase = true;
	        } else if (Character.isDigit(pswChar)) {
	        	containsAtLeastOneDigit = true;
	        } //TODO - will be used if special char validation required in future
	        /*else if (SPECIAL_CHARACTERS.indexOf(String.valueOf(pswChar)) >= 0) {
	        	containsAtLeastOneSpecialCharecter = true;
	        }*/
	    }
	    if(containsAtLeastOneUpperCase && containsAtLeastOneLowerCase && containsAtLeastOneDigit ){
	    	return ValidationResponse.createValidResponse();
	    }else {
			return ValidationResponse
					.createInvalidResponse("Invalid password format. Password must have atleast one uppercase and one lowercase alphabet and one digit.");
		}
	    
	   
	}

}
