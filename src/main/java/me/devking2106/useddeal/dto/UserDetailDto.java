package me.devking2106.useddeal.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.devking2106.useddeal.entity.User;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto {
	private Long id;
	private String locationName;
	private String nickname;
	private LocalDateTime regDate;

	public static UserDetailDto userToDetailDto(User user) {
		return UserDetailDto.builder()
			.id(user.getId())
			.locationName(user.getLocationName())
			.nickname(user.getNickname())
			.regDate(user.getRegDate())
			.build();
	}
}
