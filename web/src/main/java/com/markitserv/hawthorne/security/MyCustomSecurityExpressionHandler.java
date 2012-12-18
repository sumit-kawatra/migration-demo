package com.markitserv.hawthorne.security;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;


public class MyCustomSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler implements MethodSecurityExpressionHandler{


	public MyCustomSecurityExpressionHandler() {
		super();
    }
	
	@Override
	public StandardEvaluationContext createEvaluationContextInternal(
			Authentication auth, MethodInvocation mi) {
		System.out.println("Using my custom handler - MyCustomSecurityExpressionHandler");
        StandardEvaluationContext ctx = super.createEvaluationContextInternal(auth, mi);
        ctx.setRootObject( new MyCustomSecurityExpressionRoot(auth) );
        return ctx;
	}
      
	@Override   
	protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
		MyCustomSecurityExpressionRoot root = new MyCustomSecurityExpressionRoot(authentication);          
		//root.setThis(invocation.getThis());          
		root.setPermissionEvaluator(getPermissionEvaluator());          
		return root;    
	}


}
