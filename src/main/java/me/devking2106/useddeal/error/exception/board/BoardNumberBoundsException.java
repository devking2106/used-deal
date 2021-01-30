package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.BadRequestException;
import me.devking2106.useddeal.error.exception.common.ExceptionStatus;

public class BoardNumberBoundsException extends BadRequestException {

	public BoardNumberBoundsException() {
		super(ExceptionStatus.BOARD_NUMBER_BOUNDS_EXCEPTION);
	}
}
