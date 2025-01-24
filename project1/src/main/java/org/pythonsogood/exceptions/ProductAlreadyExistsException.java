package org.pythonsogood.exceptions;

import org.springframework.http.HttpStatus;

public class ProductAlreadyExistsException extends RequestException{
	public ProductAlreadyExistsException(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}
}
