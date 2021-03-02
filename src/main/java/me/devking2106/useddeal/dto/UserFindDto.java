package me.devking2106.useddeal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserFindDto {
	private final Long id;
	private final String nickname;
	private final String locationName;
}
