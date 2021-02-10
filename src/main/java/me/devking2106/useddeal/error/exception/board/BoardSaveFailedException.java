package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.StatusException;

public class BoardSaveFailedException extends StatusException {

	public BoardSaveFailedException() {
		super(ExceptionStatus.BOARD_SAVE_FAILED_EXCEPTION);
	}

}
