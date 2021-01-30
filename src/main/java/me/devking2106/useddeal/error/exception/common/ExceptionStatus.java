package me.devking2106.useddeal.error.exception.common;

import lombok.RequiredArgsConstructor;
import me.devking2106.useddeal.common.utils.type.EnumType;

@RequiredArgsConstructor
public enum ExceptionStatus implements EnumType {

	BOARD_NOT_FOUND_EXCEPTION(4101, "게시글 검색 결과가 없습니다", "게시글 결과가 없을 경우"),
	BOARD_SAVE_FAILED_EXCEPTION(4102, "게시글 저장에 실패 했습니다", "게시글 저장에 실패할 경우"),
	BOARD_NUMBER_BOUNDS_EXCEPTION(4103, "게시글 번호는 0보다 커야합니다", "게시글 번호가 0보다 작을 경우"),
	BOARD_LOCATION_RANGE_BOUNDS_EXCEPTION(4199, "게시글 지역 검색 범위가 벗어났습니다. 0~15 사이의 값을 입력해주세요","검색 범위가 벗어났을때 (임시)"),
	LOCATION_NOT_MATCH_EXCEPTION(4202, "동네가 일치하지 않습니다. 글쓰기를 하려면 동네인증이 필요합니다. ", "유저 동네와 게시글 동네가 일치하지 않을 경우"),

	RUN_TIME_EXCEPTION(500, "런타임 에러", "런타임 에러"),
	NOT_FOUND_EXCEPTION(404, "요청한 리소스가 존재하지 않습니다.", "요청한 리소스가 존재하지 않을 경우");
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
