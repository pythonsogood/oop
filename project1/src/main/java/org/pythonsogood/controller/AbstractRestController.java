package org.pythonsogood.controller;

import org.json.JSONObject;
import org.pythonsogood.exceptions.RequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

public abstract class AbstractRestController {
	@ExceptionHandler(RequestException.class)
	public ResponseEntity<Object> handleRequestException(HttpServletRequest req, RequestException ex) {
		JSONObject response = new JSONObject();
		response.put("message", ex.getMessage());

		String detail = ex.getDetail();
		if (detail != null) {
			response.put("detail", detail);
		}

		return ResponseEntity.status(ex.getHttpStatusCode()).contentType(MediaType.APPLICATION_JSON).body(response.toString());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex) {
		JSONObject response = new JSONObject();
		response.put("message", "Bad request");

		String detail = "";
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			detail = detail.concat(String.format("%s: %s\n", fieldName, errorMessage));
		}
		response.put("detail", detail.trim());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(response.toString());
	}
}
