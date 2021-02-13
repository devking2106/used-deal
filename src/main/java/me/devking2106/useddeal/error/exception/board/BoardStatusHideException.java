package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class BoardStatusHideException extends UsedDealException {

	public BoardStatusHideException() {
		super(ExceptionStatus.BOARD_STATUS_HIDE_EXCEPTION);
	}
}
