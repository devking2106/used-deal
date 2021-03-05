package me.devking2106.useddeal.error.exception.user;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class UserSaveFailedException extends UsedDealException {
	public UserSaveFailedException() {
		super(ExceptionStatus.USER_SAVE_FAILED_EXCEPTION);
	}
}
