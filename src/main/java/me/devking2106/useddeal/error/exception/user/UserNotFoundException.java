package me.devking2106.useddeal.error.exception.user;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class UserNotFoundException extends UsedDealException {

	public UserNotFoundException() {
		super(ExceptionStatus.USER_NOT_FOUND_EXCEPTION);
	}
}
