package me.devking2106.useddeal.error.exception.common;

public class BadRequestException extends StatusException {

	public BadRequestException(ExceptionStatus exceptionStatus) {
		super(exceptionStatus);
	}

	public BadRequestException(ExceptionStatus exceptionStatus, String message) {
		super(exceptionStatus, message);
	}
}
