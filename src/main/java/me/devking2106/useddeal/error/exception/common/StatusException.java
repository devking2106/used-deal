package me.devking2106.useddeal.error.exception.common;

import lombok.Getter;

@Getter
public class StatusException extends RuntimeException {

	private final int status;

	public StatusException(ExceptionStatus exceptionStatus) {
		super(exceptionStatus.getMessage());
		this.status = exceptionStatus.getStatus();
	}

	public StatusException(ExceptionStatus exceptionStatus, String message) {
		super(message);
		this.status = exceptionStatus.getStatus();
	}
}
