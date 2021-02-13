package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.entity.Board;
import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class BoardStatusFailedException extends UsedDealException {

	public BoardStatusFailedException(Board.Status message) {
		super(ExceptionStatus.BOARD_STATUS_FAILED_EXCEPTION, message + " 상태 변경을 실패 했습니다");

	}
}
