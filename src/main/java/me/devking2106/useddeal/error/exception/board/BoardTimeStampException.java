package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class BoardTimeStampException extends UsedDealException {

	public BoardTimeStampException(String message) {
		super(ExceptionStatus.BOARD_TIMESTAMP_EXCEPTION, message);
	}
}
