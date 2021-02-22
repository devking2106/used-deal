package me.devking2106.useddeal.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Board {

	public enum Status {
		SALE, SALE_COMPLETED, TRADE, HIDE, RESERVATION, PULL
	}

	public enum FilterType {
		TITLE, CONTENT, TITLE_OR_CONTENT
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
	private final boolean isPull;
	private final Double latitude;
	private final Double longitude;

}
