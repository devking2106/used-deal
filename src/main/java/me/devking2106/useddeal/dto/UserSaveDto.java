package me.devking2106.useddeal.dto;

import static me.devking2106.useddeal.common.utils.SHA256Util.*;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.devking2106.useddeal.entity.Location;
import me.devking2106.useddeal.entity.User;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveDto {

	@NotBlank
	private String userId;
	@NotBlank
	private String locationName;
	@NotBlank
	@Size(min = 8, max = 20)
	private String password;
	@NotBlank
	@Size(min = 2, max = 20)
	private String nickname;
	@Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
	private String phone;
	@Email
	private String email;

	public User toEntity(Location location) {
		LocalDateTime saveTime = LocalDateTime.now();
		return User.builder()
			.userId(userId)
			.locationName(locationName)
			.locationId(location.getId())
			.password(encodeSHA256(password))
			.nickname(nickname)
			.phone(phone)
			.email(email)
			.regDate(saveTime)
			.modDate(saveTime)
			.loginDate(saveTime)
			.role(User.Role.USER)
			.status(User.Status.ACTIVE)
			.saleCount(0L)
			.imagePath("C:/images/temp.jpg")
			.latitude(location.getLatitude())
			.longitude(location.getLongitude())
			.authCount(0L)
			.build();
	}
}
