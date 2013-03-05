package com.markitserv.msws.web;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import com.markitserv.msws.AbstractWebserviceResult;
import com.markitserv.msws.ExceptionResult;
import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.command.CommandDispatcher;
import com.markitserv.msws.command.ErrorCommand;
import com.markitserv.msws.exceptions.MswsException;
import com.markitserv.msws.exceptions.ProgrammaticException;
import com.markitserv.msws.internal.Constants;

@Controller
@RequestMapping(value = "/")
public class MswsController {

	@Autowired
	private HttpParamsToActionCommand actionCmdBuilder;
	@Autowired
	private CommandDispatcher dispatcher;
	@Autowired
	private RequestContextHolderWrapper reqContextHolder;

	Logger log = LoggerFactory.getLogger(MswsController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	AbstractWebserviceResult performActionReq(WebRequest req) {

		AbstractWebserviceResult result = null;
		String uuid = null;
		uuid = (String) req.getAttribute(Constants.UUID, RequestAttributes.SCOPE_REQUEST);
		log.info("Uuid is " + uuid);

		try {
			ActionCommand actionCmd = actionCmdBuilder.buildActionCommandFromHttpParams(req
					.getParameterMap());
			result = (ActionResult) dispatcher.dispatchReqRespCommand(actionCmd);
		} catch (MswsException mwwsException) {

			// If the exception belongs to the programmatic exception the mail goes
			// to error distribution group
			if (mwwsException instanceof ProgrammaticException) {
				dispatcher.dispatchAsyncCommand(new ErrorCommand(mwwsException));
			}

			result = new ExceptionResult(mwwsException);

		} catch (Exception exception) {

			ProgrammaticException programmaticException = new ProgrammaticException(
					"Unknown error occured.  See stack trace.", exception);

			dispatcher.dispatchAsyncCommand(new ErrorCommand(programmaticException));

			result = new ExceptionResult(programmaticException);
		}

		HttpSession session = reqContextHolder.getCurrentSession();

		DateTime expires = new DateTime(DateTimeZone.UTC);
		expires = expires.plusSeconds(session.getMaxInactiveInterval());

		result.getMetaData().setSessionExpires(expires);
		result.getMetaData().setRequestId(uuid);

		return result;
	}

	public void setDispatcher(CommandDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void setActionCmdBuilder(HttpParamsToActionCommand actionCmdBuilder) {
		this.actionCmdBuilder = actionCmdBuilder;
	}

	public void setReqContextHolder(RequestContextHolderWrapper reqContextHolder) {
		this.reqContextHolder = reqContextHolder;
	}
}