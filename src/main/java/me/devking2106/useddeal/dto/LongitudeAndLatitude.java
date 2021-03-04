package me.devking2106.useddeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LongitudeAndLatitude {
	private Double latitude;
	private Double longitude;
}
