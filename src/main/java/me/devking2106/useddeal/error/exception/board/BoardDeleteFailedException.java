package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class BoardDeleteFailedException extends UsedDealException {
	public BoardDeleteFailedException() {
		super(ExceptionStatus.BOARD_DELETE_FAILED_EXCEPTION);
	}
}
