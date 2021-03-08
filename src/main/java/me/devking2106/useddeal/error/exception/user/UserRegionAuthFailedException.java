package me.devking2106.useddeal.error.exception.user;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class UserRegionAuthFailedException extends UsedDealException {
	public UserRegionAuthFailedException() {
		super(ExceptionStatus.USER_REGION_AUTH_FAILED_EXCEPTION);
	}
}
