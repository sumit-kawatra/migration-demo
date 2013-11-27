package com.markitserv.msws.internal.web;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.resp.ActionResult;
import com.markitserv.msws.beans.RequestContext;
import com.markitserv.msws.beans.RequestInfo;
import com.markitserv.msws.beans.SessionInfo;
import com.markitserv.msws.beans.UploadedFile;
import com.markitserv.msws.internal.Constants;
import com.markitserv.msws.internal.action.AbstractWebserviceResult;
import com.markitserv.msws.internal.action.ActionDispatcher;
import com.markitserv.msws.internal.action.ExceptionResult;
import com.markitserv.msws.internal.exceptions.MswsException;
import com.markitserv.msws.internal.exceptions.ProgrammaticException;
import com.markitserv.msws.util.MswsAssert;
import com.markitserv.msws.util.SecurityAndSessionHelper;
import com.markitserv.msws.web.AbstractSessionInfoBuilder;

@Controller
@RequestMapping(value = "/svc")
public class MswsController implements ServletContextAware {

	Logger log = LoggerFactory.getLogger(MswsController.class);

	@Autowired
	private HttpParamsToActionCommand actionCmdBuilder;
	@Autowired
	private ActionDispatcher dispatcher;
	@Autowired
	private SecurityAndSessionHelper securitySessionUtil;
	@Resource(name = "sessionInfoBuilder")
	private AbstractSessionInfoBuilder sessionBuilder;

	private ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public @ResponseBody
	AbstractWebserviceResult performActionReqPost(NativeWebRequest req) {
		return safeHandleRequest(RequestMethod.POST, req);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	AbstractWebserviceResult performActionReq(NativeWebRequest req) {
		return safeHandleRequest(RequestMethod.GET, req);
	}

	private AbstractWebserviceResult safeHandleRequest(RequestMethod reqMethod,
			NativeWebRequest req) {

		String reqId = (String) req.getAttribute(Constants.HTTP_ATTRIB_UUID,
				RequestAttributes.SCOPE_REQUEST);

		MswsAssert.mswsAssert(reqId != null && !StringUtils.isBlank(reqId),
				"UUID not found on the request.");

		AbstractWebserviceResult res = null;

		try {
			res = handleRequest(reqId, reqMethod, req);
		} catch (Exception e) {

			if (!(e instanceof MswsException)) {
				e = new ProgrammaticException(
						"Unknown error occured.  See stack trace.", e);
			}

			// Internal errors should be logged / notified
			if (e instanceof ProgrammaticException) {

				log.error("Server Sider Error", e);

				// TODO we're ignoreing errors!
				// this.dispatcher.dispatchAsyncCommand(new ErrorCommand(e));
			}

			AbstractWebserviceResult errorResult = new ExceptionResult(
					(MswsException) e);

			res = errorResult;
		}

		res.getMetaData().setRequestId(reqId);
		return res;

	}

	private AbstractWebserviceResult handleRequest(String reqId,
			RequestMethod reqMethod, NativeWebRequest req) throws Exception {

		AbstractWebserviceResult result = null;

		DateTime reqTimestamp = (DateTime) req.getAttribute(
				Constants.HTTP_ATTRIB_TIMESTAMP,
				RequestAttributes.SCOPE_REQUEST);

		ActionCommand actionCmd = actionCmdBuilder
				.buildActionCommandFromHttpParams(req.getParameterMap());

		if (reqMethod == RequestMethod.POST) {
			Map<String, Object> postFields;
			postFields = pullPostParametersFromRequest(req
					.getNativeRequest(HttpServletRequest.class));

			actionCmd.addParameters(postFields);
		}

		// Build request context
		RequestInfo reqInfo = new RequestInfo();
		reqInfo.setRequestId(reqId);
		reqInfo.setTimestamp(reqTimestamp);

		RequestContext reqCtx = new RequestContext();
		SessionInfo sessionInfo = this.sessionBuilder.buildSessionInfo();
		reqCtx.setSession(sessionInfo);

		actionCmd.setRequestContext(reqCtx);

		result = (ActionResult) dispatcher.dispatch(actionCmd);

		return result;

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
				f.setFileName(item.getName());

				postFields.put(item.getFieldName(), f);

			} else {
				postFields.put(item.getFieldName(), item.getString());
			}
		}

		return postFields;
	}

	// @RequestMapping(value = "", method = RequestMethod.GET)

	public void setDispatcher(ActionDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void setActionCmdBuilder(HttpParamsToActionCommand actionCmdBuilder) {
		this.actionCmdBuilder = actionCmdBuilder;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;

	}
}