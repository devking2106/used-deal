package me.devking2106.useddeal.error.exception.common;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ExceptionStatus {

	// Board
	BOARD_NOT_FOUND_EXCEPTION(4101, "게시글 검색 결과가 없습니다."),
	BOARD_SAVE_FAILED_EXCEPTION(4102, "게시글 저장에 실패 했습니다."),
	BOARD_STATUS_HIDE_EXCEPTION(4103, "숨긴 게시글은 볼 수 없습니다."),

	// Location
	LOCATION_NOT_MATCH_EXCEPTION(4201, "동네가 일치하지 않습니다. 글쓰기를 하려면 동네인증이 필요합니다."),

	// Common
	RUN_TIME_EXCEPTION(500, "런타임 에러"),
	NOT_FOUND_EXCEPTION(404, "요청한 리소스가 존재하지 않습니다."),
	INVALID_TYPE_VALUE_EXCEPTION(400, "유효하지 않는 Type의 값입니다. 입력 값을 확인 해주세요."),
	INVALID_FORMAT_EXCEPTION(400, "유효하지 않는 Type 입니다. Type을 확인 해주세요."),
	INVALID_INPUT_VALUE(400, "유효하지 않는 입력 값입니다."),
	METHOD_NOT_ALLOWED(405, "지원하지 않은 HTTP Method 입니다."),
	;

	private final int status;
	private final String message;

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

}
