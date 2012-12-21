package com.markitserv.hawthorne.web;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.markitserv.mwws.ActionCommand;
import com.markitserv.mwws.ActionCommandBuilder;
import com.markitserv.mwws.ActionResult;
import com.markitserv.mwws.CommandDispatcher;

@Controller
@RequestMapping(value = "/")
public class HawthorneController {

	@Autowired
	private ActionCommandBuilder actionCmdBuilder;
	@Autowired
	private CommandDispatcher dispatcher;

	Logger log = LoggerFactory.getLogger(HawthorneController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	ActionResult performActionReq(WebRequest req) {

		ActionCommand actionCmd = actionCmdBuilder.buildActionCommandFromHttpParams(req
				.getParameterMap());

		return (ActionResult) dispatcher.dispatchReqRespCommand(actionCmd);
	}
}