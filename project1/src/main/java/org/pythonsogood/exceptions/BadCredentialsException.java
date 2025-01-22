package org.pythonsogood.exceptions;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends RequestException {
	public BadCredentialsException(String message) {
		super(message, HttpStatus.FORBIDDEN);
	}
}
