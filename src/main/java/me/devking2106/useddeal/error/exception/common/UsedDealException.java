package me.devking2106.useddeal.error.exception.common;

import org.springframework.http.HttpStatus;

public class UsedDealException extends RuntimeException {

	private final ExceptionStatus responseStatus;

	public UsedDealException(ExceptionStatus responseStatus) {
		super(responseStatus.getMessage());
		this.responseStatus = responseStatus;
	}

	public UsedDealException(ExceptionStatus responseStatus, String message) {
		super(message);
		this.responseStatus = responseStatus;
	}

	public HttpStatus getHttpStatus() {
		return responseStatus.getHttpStatus();
	}

	public String getStatus() {
		return String.valueOf(responseStatus.getStatus());
	}
}
