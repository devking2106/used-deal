package me.devking2106.useddeal.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@Getter
@Builder
@ToString
public class Board {

	@Getter
	@RequiredArgsConstructor
	public enum Status {
		SALE("판매중"), SALE_COMPLETED("거래완료"), TRADE("거래중"), HIDE("숨김"), RESERVATION("예약중");

		private final String status;

	}

	private final Long id;
	private final Long userId;
	private final Long locationId;
	private final String locationName;
	private final String title;
	private final String content;
	private final Long price;
	private final Long categoryId;
	private final Board.Status status;
	private final LocalDateTime regDate;
	private final LocalDateTime modDate;
	private final LocalDateTime boardDate;
	private final boolean isPriceSuggest;
	private final boolean isPull;
	private final Double latitude;
	private final Double longitude;

}
