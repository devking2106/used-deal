package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class BoardSaveFailedException extends UsedDealException {

	public BoardSaveFailedException(Long userId, String title) {
		super(ExceptionStatus.BOARD_SAVE_FAILED_EXCEPTION, "게시글 저장 실패 : user id = " + userId
			+ " , title = " + title);
	}

}
