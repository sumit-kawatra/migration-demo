package com.markitserv.ssa.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.markitserv.rest.RestRuntimeException;

public class HttpExceptions {
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public static class Http404 extends RestRuntimeException {

		private static final long serialVersionUID = 1L;

		public Http404() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Http404(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public Http404(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public Http404(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}
		
	}
}
