package me.devking2106.useddeal.error.exception.common;

import org.springframework.http.HttpStatus;

public class StatusException extends RuntimeException {

	private final ExceptionStatus responseStatus;

	public StatusException(ExceptionStatus responseStatus) {
		super(responseStatus.getMessage());
		this.responseStatus = responseStatus;
	}

	public StatusException(ExceptionStatus responseStatus, String message) {
		super(message);
		this.responseStatus = responseStatus;
	}

	public HttpStatus getHttpStatus() {
		return responseStatus.getHttpStatus();
	}

}
