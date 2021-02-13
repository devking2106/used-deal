package me.devking2106.useddeal.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

@Getter
@AllArgsConstructor
public class ExceptionResponseInfo {
	private String status;
	private String message;

	public static ExceptionResponseInfo of(UsedDealException usedDealException) {
		return new ExceptionResponseInfo(usedDealException.getStatus(), usedDealException.getMessage());
	}
}
