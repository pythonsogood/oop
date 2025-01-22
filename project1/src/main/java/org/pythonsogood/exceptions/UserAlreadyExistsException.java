package org.pythonsogood.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends RequestException{
	public UserAlreadyExistsException(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}
}
