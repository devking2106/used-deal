package me.devking2106.useddeal.error.exception.location;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class TownNotMatchException extends UsedDealException {

	public TownNotMatchException(String message) {
		super(ExceptionStatus.LOCATION_NOT_MATCH_EXCEPTION, "동네가 일치하지 않습니다. 글쓰기를 하려면 " + message + " 동네인증이 필요합니다 ");
	}
}
