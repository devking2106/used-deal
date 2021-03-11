package me.devking2106.useddeal.error.exception.board;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class BoardDateLessThanTwoDaysException extends UsedDealException {

	public BoardDateLessThanTwoDaysException(String message) {
		super(ExceptionStatus.BOARD_DATE_LESS_THAN_TWO_DAYS_EXCEPTION, "게시일이 2일이 지나지 않았습니다. 경과시간 = "+message);
	}
}
