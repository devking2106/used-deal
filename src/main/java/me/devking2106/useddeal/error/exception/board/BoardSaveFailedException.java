package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.BadRequestException;
import me.devking2106.useddeal.error.exception.common.ExceptionStatus;

public class BoardSaveFailedException extends BadRequestException {

	public BoardSaveFailedException() {
		super(ExceptionStatus.BOARD_SAVE_FAILED_EXCEPTION);
	}

}
