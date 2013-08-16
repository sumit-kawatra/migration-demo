package com.markitserv.msws.web;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;

/**
 * Encapsulates an uploaded file.
 * 
 * @author roy.truelove
 * 
 */
public class UploadedFile {

	private InputStream inputStream;
	private String contentType;
	private String fileName;

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

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileExtention() {
		return StringUtils.substringAfterLast(this.getFileName(), ".");
	}
}
