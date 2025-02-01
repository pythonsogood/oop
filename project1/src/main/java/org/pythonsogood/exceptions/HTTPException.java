package org.pythonsogood.exceptions;

import org.springframework.http.HttpStatusCode;

public interface HTTPException {
	public String getMessage();

	public HttpStatusCode getHttpStatusCode();

	public String getDetail();
}
