package com.markitserv.msws.web;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

/**
 * From http://jpgmr.wordpress.com/2010/07/28/tutorial-implementing-a-servlet-filter-for-jsonp-callback-with-springs-delegatingfilterproxy/
 * @author roy.truelove
 *
 */
public class FilterServletOutputStream extends ServletOutputStream {

	private DataOutputStream stream;

	public FilterServletOutputStream(OutputStream output) {
		stream = new DataOutputStream(output);
	}

	public void write(int b) throws IOException {
		stream.write(b);
	}

	public void write(byte[] b) throws IOException {
		stream.write(b);
	}

	public void write(byte[] b, int off, int len) throws IOException {
		stream.write(b, off, len);
	}
}
