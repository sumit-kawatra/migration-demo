package com.markitserv.msws.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.markitserv.msws.beans.MswsRequestContext;
import com.markitserv.msws.internal.request.MswsRequestContextHelper;
import com.markitserv.msws.internal.web.SpringSessionAndSecurityWrappers;

@Service
public class MswsRequestContextHolder {

	@Autowired
	private MswsRequestContextHelper helper;

	public MswsRequestContext getRequestContext() {
		return helper.getMswsRequestContextFromThread();
	}

	public boolean requestContextIsPopulated() {
		return false;
	}
}
