package com.markitserv.mwws.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import com.markitserv.mwws.ExceptionResult;
import com.markitserv.mwws.GenericResult;
import com.markitserv.mwws.Util.Constants;
import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.action.ActionResult;
import com.markitserv.mwws.command.CommandDispatcher;
import com.markitserv.mwws.exceptions.MwwsException;
import com.markitserv.mwws.exceptions.ProgrammaticException;

@Controller
@RequestMapping(value = "/")
public class MwwsController {

	@Autowired
	private HttpParamsToActionCommand actionCmdBuilder;
	@Autowired
	private CommandDispatcher dispatcher;

	Logger log = LoggerFactory.getLogger(MwwsController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	GenericResult performActionReq(WebRequest req) {

		GenericResult result = null;
		String uuid = null;
		uuid = (String) req.getAttribute(Constants.UUID,
				RequestAttributes.SCOPE_REQUEST);
		log.info("Uuid is " + uuid);

		try {
			ActionCommand actionCmd = actionCmdBuilder
					.buildActionCommandFromHttpParams(req.getParameterMap());
			result = (ActionResult) dispatcher
					.dispatchReqRespCommand(actionCmd);
		} catch (MwwsException mwwsException) {
			// TODO send exception in an ErrorCommand to the dispatcher. Logging
			// for now
			log.error("Unknown Exception", mwwsException);
			result = new ExceptionResult(mwwsException);
		} catch (Exception exception) {
			// TODO send exception in an ErrorCommand to the dispatcher. Logging
			// for now
			log.error("Unknown Exception", exception);
			ProgrammaticException programmaticException = new ProgrammaticException(
					"Unknwon error occured.", exception);
			result = new ExceptionResult(programmaticException);
		}

		result = setUuidInRequest(result, uuid);
		return result;
	}

	private GenericResult setUuidInRequest(GenericResult result, String uuid) {
		try {
			result.getMetaData().setRequestId(uuid);
		} catch (Exception exception) {
			ProgrammaticException programmaticException = new ProgrammaticException(
					"Unknown error occured.", exception);
			result = new ExceptionResult(programmaticException);
		}
		return result;
	}

	public void setDispatcher(CommandDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void setActionCmdBuilder(HttpParamsToActionCommand actionCmdBuilder) {
		this.actionCmdBuilder = actionCmdBuilder;
	}
}