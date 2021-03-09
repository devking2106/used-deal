package me.devking2106.useddeal.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class DateInfo {
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	private LocalDateTime boardDate;
}
