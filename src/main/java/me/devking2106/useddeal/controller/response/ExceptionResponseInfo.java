package me.devking2106.useddeal.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionResponseInfo {
	private final String status;
	private final String message;

	public static ExceptionResponseInfo of(UsedDealException usedDealException) {
		return new ExceptionResponseInfo(usedDealException.getStatus(), usedDealException.getMessage());
	}
}
