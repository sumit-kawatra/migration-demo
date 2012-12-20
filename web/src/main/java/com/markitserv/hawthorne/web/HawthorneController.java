package com.markitserv.hawthorne.web;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.markitserv.hawthorne.util.HardcodedData;
import com.markitserv.mwws.ActionCommand;
import com.markitserv.mwws.CommonParamKeys;
import com.markitserv.mwws.HttpParamsProcessor;
import com.markitserv.mwws.MalformedFiltersException;
import com.markitserv.mwws.MultipleParameterValuesException;

@Controller
@RequestMapping(value = "/")
public class HawthorneController {

	@Autowired
	private HttpParamsProcessor filterProc;

	Logger log = LoggerFactory.getLogger(HawthorneController.class);

	@Autowired
	private HardcodedData data;

	// public @ResponseBody Response preformAction() {
	// return null;
	// }

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	String performAction(@RequestParam(value = "Action") String action,
			WebRequest req) {

		ActionCommand cmd = new ActionCommand();

		log.info("Called with action:" + action);

		Map<String, Object> processedParams = 
				filterProc.processParameters(req.getParameterMap());

		log.info("Params:");
		for (String key : processedParams.keySet()) {
			log.info("Key {}: Value {}", key, processedParams.get(key)
					.toString());
		}

		return "Success";
	}

}