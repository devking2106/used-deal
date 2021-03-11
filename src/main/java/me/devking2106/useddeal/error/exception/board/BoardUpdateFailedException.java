package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class BoardUpdateFailedException extends UsedDealException {
	public BoardUpdateFailedException() {
		super(ExceptionStatus.BOARD_UPDATE_FAILED_EXCEPTION, "게시글 수정을 실패했습니다");
	}
}
