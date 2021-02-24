package me.devking2106.useddeal.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Location {

	private final Long id;
	private final String locationName;
	private final String province;
	private final String city;
	private final String town;
	private final Double latitude;
	private final Double longitude;
}
