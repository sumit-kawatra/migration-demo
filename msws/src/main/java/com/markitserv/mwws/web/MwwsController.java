package com.markitserv.mwws.web;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.action.ActionResult;
import com.markitserv.mwws.command.CommandDispatcher;
import com.markitserv.mwws.web.HttpParamsToActionCommand;

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
	ActionResult performActionReq(WebRequest req) {

		ActionCommand actionCmd = actionCmdBuilder.buildActionCommandFromHttpParams(req
				.getParameterMap());

		return (ActionResult) dispatcher.dispatchReqRespCommand(actionCmd);
	}
}