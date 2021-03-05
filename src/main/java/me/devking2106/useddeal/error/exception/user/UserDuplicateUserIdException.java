package me.devking2106.useddeal.error.exception.user;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class UserDuplicateUserIdException extends UsedDealException {
	public UserDuplicateUserIdException() {
		super(ExceptionStatus.USER_DUPLICATE_USERID_EXCEPTION);
	}
}
