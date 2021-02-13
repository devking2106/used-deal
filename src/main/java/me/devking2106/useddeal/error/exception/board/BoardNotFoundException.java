package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class BoardNotFoundException extends UsedDealException {

	public BoardNotFoundException() {
		super(ExceptionStatus.BOARD_NOT_FOUND_EXCEPTION);
	}
}
