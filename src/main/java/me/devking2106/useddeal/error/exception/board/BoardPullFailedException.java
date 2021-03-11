package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class BoardPullFailedException extends UsedDealException {

	public BoardPullFailedException() {
		super(ExceptionStatus.BOARD_PULL_FAILED_EXCEPTION);
	}

}
