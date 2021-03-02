package me.devking2106.useddeal.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class User {

	public enum Role {
		USER, MANAGER
	}

	public enum Status {
		ACTIVE, DELETED
	}

	private final Long id;
	private final String userId;
	private final String locationName;
	private final Long locationId;
	private final String password;
	private final String nickname;
	private final String phone;
	private final String email;
	private final LocalDateTime regDate;
	private final LocalDateTime modDate;
	private final LocalDateTime loginDate;
	private final Role role;
	private final Status status;
	private final Long saleCount;
	private final String imagePath;
	private final Double latitude;
	private final Double longitude;
	private final Long authCount;
}
