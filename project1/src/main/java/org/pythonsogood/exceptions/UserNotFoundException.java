package org.pythonsogood.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RequestException {
	public UserNotFoundException(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}
}