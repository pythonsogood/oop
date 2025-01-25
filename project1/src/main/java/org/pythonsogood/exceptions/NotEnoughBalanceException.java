package org.pythonsogood.exceptions;

import org.springframework.http.HttpStatus;

public class NotEnoughBalanceException extends RequestException {
	public NotEnoughBalanceException(String message) {
		super(message, HttpStatus.FORBIDDEN);
	}
}
