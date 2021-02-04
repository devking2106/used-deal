package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.BadRequestException;
import me.devking2106.useddeal.error.exception.common.ExceptionStatus;

public class BoardStatusHideException extends BadRequestException {

	public BoardStatusHideException() {
		super(ExceptionStatus.BOARD_STATUS_HIDE_EXCEPTION);
	}
}
