package com.markitserv.msws.web;

import java.io.InputStream;

/**
 * Encapsulates an uploaded file.
 * @author roy.truelove
 *
 */
public class UploadedFile {
	
	private InputStream inputStream;
	private String contentType;
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
