package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.BadRequestException;
import me.devking2106.useddeal.error.exception.common.ExceptionStatus;

public class BoardNotFoundException extends BadRequestException {

	public BoardNotFoundException() {
		super(ExceptionStatus.BOARD_NOT_FOUND_EXCEPTION);
	}
}
