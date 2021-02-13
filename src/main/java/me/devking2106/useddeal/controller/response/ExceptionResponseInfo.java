package me.devking2106.useddeal.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.devking2106.useddeal.error.exception.common.StatusException;

@Getter
@AllArgsConstructor
public class ExceptionResponseInfo {
	private String status;
	private String message;

	public static ExceptionResponseInfo of(StatusException statusException) {
		return new ExceptionResponseInfo(statusException.getStatus(), statusException.getMessage());
	}

}
