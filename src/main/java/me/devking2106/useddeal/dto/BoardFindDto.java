package me.devking2106.useddeal.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class BoardFindDto {
	private final Long id;
	private final String title;
	private final String locationName;
	private final Long price;
	private final Long categoryId;
	private final String status;
	private final LocalDateTime boardDate;
	private final boolean isPull;
}
