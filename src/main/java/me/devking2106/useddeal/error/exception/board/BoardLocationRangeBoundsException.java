package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.BadRequestException;
import me.devking2106.useddeal.error.exception.common.ExceptionStatus;

public class BoardLocationRangeBoundsException extends BadRequestException {
	public BoardLocationRangeBoundsException() {
		super(ExceptionStatus.BOARD_LOCATION_RANGE_BOUNDS_EXCEPTION);
	}
}
