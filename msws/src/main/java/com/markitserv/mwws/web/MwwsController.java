package com.markitserv.mwws.web;

import java.security.Principal;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import com.markitserv.mwws.CommonConstants;
import com.markitserv.mwws.ExceptionResult;
import com.markitserv.mwws.GenericResult;
import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.action.ActionResult;
import com.markitserv.mwws.command.CommandDispatcher;
import com.markitserv.mwws.exceptions.MwwsException;

@Controller
@RequestMapping(value = "/")
public class MwwsController {

	@Autowired
	private HttpParamsToActionCommand actionCmdBuilder;
	@Autowired
	private CommandDispatcher dispatcher;
	@Autowired
	private Environment env;

	Logger log = LoggerFactory.getLogger(MwwsController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	GenericResult performActionReq(WebRequest req) {
		GenericResult result = null;
		String uuid = null;
		
		uuid = (String) req.getAttribute(CommonConstants.UUID,
				RequestAttributes.SCOPE_REQUEST);
		
		try {
			ActionCommand actionCmd = actionCmdBuilder
					.buildActionCommandFromHttpParams(req.getParameterMap());
			result = (ActionResult) dispatcher
					.dispatchReqRespCommand(actionCmd);
			
		// TODO catch all exceptions
		} catch (MwwsException exception) {
			result = new ExceptionResult(exception);
		}
		
		log.info("Uuid from requestRegistry is " + uuid);
		result.getMetaData().setRequestId(uuid);
		return result;
	}
}