package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class BoardNotMatchUserIdException extends UsedDealException {

	public BoardNotMatchUserIdException() {
		super(ExceptionStatus.BOARD_NOT_MATCH_USERID_EXCEPTION);
	}
}
