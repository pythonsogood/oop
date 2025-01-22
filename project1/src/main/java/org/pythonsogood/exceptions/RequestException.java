package org.pythonsogood.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class RequestException extends Exception {
	private HttpStatusCode httpStatusCode = HttpStatus.BAD_REQUEST;
	private String detail;

	public RequestException(String message, HttpStatusCode httpStatusCode) {
		super(message);
		this.httpStatusCode = httpStatusCode;
	}

	public RequestException(String message) {
		super(message);
	}

	public HttpStatusCode getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
