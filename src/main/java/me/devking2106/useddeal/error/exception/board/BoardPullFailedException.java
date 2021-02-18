package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class BoardPullFailedException extends UsedDealException {

	public BoardPullFailedException() {
		super(ExceptionStatus.BOARD_PULL_FAILED_EXCEPTION, "끌어 올리기를 실패 했습니다. 경과 시간이 2일이 지나지 않았습니다. ");
	}

}
