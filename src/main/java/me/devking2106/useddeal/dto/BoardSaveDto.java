package me.devking2106.useddeal.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.devking2106.useddeal.entity.Board;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardSaveDto {

	@NotBlank
	private String title;

	@NotBlank
	private String content;

	@Min(0)
	private long price;

	@NotNull
	@Min(0)
	private Long categoryId;

	@NotBlank
	private String locationName;

	public Board toEntity(Long userId, String locationName, Long locationId, double latitude, double longitude) {
		LocalDateTime saveTime = LocalDateTime.now();
		return Board.builder()
			.userId(userId)
			.locationName(locationName)
			.locationId(locationId)
			.title(title)
			.content(content)
			.price(price)
			.categoryId(categoryId)
			.status(Board.Status.SALE)
			.regDate(saveTime)
			.modDate(saveTime)
			.boardDate(saveTime)
			.isPull(false)
			.latitude(latitude)
			.longitude(longitude)
			.build();
	}

}
