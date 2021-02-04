package me.devking2106.useddeal.error.exception.common;

import lombok.RequiredArgsConstructor;
import me.devking2106.useddeal.common.utils.type.EnumType;

@RequiredArgsConstructor
public enum ExceptionStatus implements EnumType {

	// Board
	BOARD_NOT_FOUND_EXCEPTION(4101, "게시글 검색 결과가 없습니다", "게시글 결과가 없을 경우"),
	BOARD_SAVE_FAILED_EXCEPTION(4102, "게시글 저장에 실패 했습니다", "게시글 저장에 실패할 경우"),
	BOARD_STATUS_HIDE_EXCEPTION(4103, "숨긴 게시글은 볼 수 없습니다", "숨긴 게시글을 조회할 경우"),
	BOARD_LOCATION_RANGE_BOUNDS_EXCEPTION(4199, "게시글 지역 검색 범위가 벗어났습니다. 0~15 사이의 값을 입력해주세요","검색 범위가 벗어났을때 (임시)"),

	// Location
	LOCATION_NOT_FOUND_EXCEPTION(4201, "일치하는 동네가 없습니다. 올바른 동네를 입력해주세요", "일치하는 동네가 없을 경우"),
	LOCATION_NOT_MATCH_EXCEPTION(4202, "동네가 일치하지 않습니다. 글쓰기를 하려면 동네인증이 필요합니다. ", "유저 동네와 게시글 동네가 일치하지 않을 경우"),

	// Common
	RUN_TIME_EXCEPTION(500, "런타임 에러", "런타임 에러"),
	NOT_FOUND_EXCEPTION(404, "요청한 리소스가 존재하지 않습니다.", "요청한 리소스가 존재하지 않을 경우"),
	INVALID_TYPE_VALUE_EXCEPTION(400, "유효하지 않는 Type의 값입니다. 입력 값을 확인 해주세요", "Type 이 일치하지 않을 때"),
	INVALID_FORMAT_EXCEPTION(400, "유효하지 않는 Type의 값입니다. 입력 값을 확인 해주세요", "Type 이 일치하지 않을 때"),
	INVALID_INPUT_VALUE(400, "유효하지 않는 입력 값입니다.", "입력을 잘못 할 때"),
	METHOD_NOT_ALLOWED(405, "지원하지 않은 HTTP Method 입니다.", "지원하지 않은 HTTP Method 를 입력했을때"),
	;

	private final int status;
	private final String message;
	private final String description;

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
