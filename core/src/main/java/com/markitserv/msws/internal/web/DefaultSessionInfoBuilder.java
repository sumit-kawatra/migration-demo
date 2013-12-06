package com.markitserv.msws.internal.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.markitserv.msws.beans.SessionInfo;
import com.markitserv.msws.web.AbstractSessionInfoBuilder;

/**
 * Default session info builder, which does not add any application-specific
 * information.
 * 
 * @author roy.truelove
 * 
 */
public class DefaultSessionInfoBuilder extends AbstractSessionInfoBuilder {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected Map<String, Object> addAttributes(Map<String, Object> attribs) {
		return attribs;
	}

}