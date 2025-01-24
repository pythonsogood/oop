package org.pythonsogood.exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends RequestException {
	public ProductNotFoundException(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}
}