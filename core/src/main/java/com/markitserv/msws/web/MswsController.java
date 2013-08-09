package com.markitserv.msws.web;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import com.markitserv.msws.AbstractWebserviceResult;
import com.markitserv.msws.ExceptionResult;
import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.command.CommandDispatcher;
import com.markitserv.msws.command.ErrorCommand;
import com.markitserv.msws.exceptions.MswsException;
import com.markitserv.msws.exceptions.ProgrammaticException;
import com.markitserv.msws.internal.Constants;
import com.markitserv.msws.internal.MswsAssert;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

@Controller
@RequestMapping(value = "/")
public class MswsController implements ServletContextAware {

	@Autowired
	private HttpParamsToActionCommand actionCmdBuilder;
	@Autowired
	private CommandDispatcher dispatcher;
	@Autowired
	private RequestContextHolderWrapper reqContextHolder;

	private ServletContext servletContext;

	Logger log = LoggerFactory.getLogger(MswsController.class);
	
	private AbstractWebserviceResult handleRequest(RequestMethod m, NativeWebRequest req) {
		
		AbstractWebserviceResult result = null;
		
		String uuid = (String) req.getAttribute(Constants.UUID,
				RequestAttributes.SCOPE_REQUEST);
		
		MswsAssert.mswsAssert(uuid != null && !StringUtils.isBlank(uuid), "UUID not found on the request.");
		
		log.info("Uuid is " + uuid);

		try {
			
			ActionCommand actionCmd = actionCmdBuilder
					.buildActionCommandFromHttpParams(req.getParameterMap());
			
			if (m == RequestMethod.POST) {
				Map<String, Object> postFields = pullPostParametersFromRequest(req
						.getNativeRequest(HttpServletRequest.class));
				
				actionCmd.addParameters(postFields);
			}
			
			result = (ActionResult) dispatcher
					.dispatchReqRespCommand(actionCmd);
			
		} catch (Exception e) {
			result = errorHander(e);
		}

		HttpSession session = reqContextHolder.getCurrentSession();

		DateTime expires = new DateTime(DateTimeZone.UTC);
		expires = expires.plusSeconds(session.getMaxInactiveInterval());

		result.getMetaData().setSessionExpires(expires);
		result.getMetaData().setRequestId(uuid);

		return result;
		
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public @ResponseBody
	AbstractWebserviceResult performActionReqPost(NativeWebRequest req) {
		return handleRequest(RequestMethod.POST, req);
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	AbstractWebserviceResult performActionReq(NativeWebRequest req) {
		return handleRequest(RequestMethod.GET, req);
	}
	
	private Map<String, Object> pullPostParametersFromRequest(
			HttpServletRequest req) throws Exception {

		Map<String, Object> postFields = new HashMap<String, Object>();

		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		File repository = (File) servletContext
				.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		List<FileItem> items;
		// Parse the request
		items = upload.parseRequest(req);

		Iterator<FileItem> i = items.iterator();

		while (i.hasNext()) {
			FileItem item = i.next();

			if (!item.isFormField()) {

				UploadedFile f = new UploadedFile();

				f.setContentType(item.getContentType());
				f.setInputStream(item.getInputStream());

				postFields.put(item.getFieldName(), f);

			} else {
				postFields.put(item.getFieldName(), item.getString());
			}
		}

		return postFields;
	}

	private AbstractWebserviceResult errorHander(Exception e) {
		
		if (!(e instanceof MswsException)) {
			e = new ProgrammaticException(
					"Unknown error occured.  See stack trace.", e);
		}
		
		// Internal errors should be logged / notified
		if (e instanceof ProgrammaticException) {
			
			dispatcher
				.dispatchAsyncCommand(new ErrorCommand(e));
		}
			
		return new ExceptionResult((MswsException) e);
	}

	// @RequestMapping(value = "", method = RequestMethod.GET)


	public void setDispatcher(CommandDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void setActionCmdBuilder(HttpParamsToActionCommand actionCmdBuilder) {
		this.actionCmdBuilder = actionCmdBuilder;
	}

	public void setReqContextHolder(RequestContextHolderWrapper reqContextHolder) {
		this.reqContextHolder = reqContextHolder;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;

	}
}