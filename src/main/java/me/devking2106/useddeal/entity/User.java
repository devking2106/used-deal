package me.devking2106.useddeal.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.devking2106.useddeal.dto.LongitudeAndLatitude;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

	public enum Role {
		USER, MANAGER
	}

	public enum Status {
		ACTIVE, DELETED
	}

	private Long id;
	private String userId;
	private String locationName;
	private Long locationId;
	private String password;
	private String nickname;
	private String phone;
	private String email;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	private LocalDateTime loginDate;
	private Role role;
	private Status status;
	private LongitudeAndLatitude longitudeAndLatitude;
}
