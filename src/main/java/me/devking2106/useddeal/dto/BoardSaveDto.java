package me.devking2106.useddeal.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.devking2106.useddeal.common.utils.type.CommonStatus;
import me.devking2106.useddeal.entity.Board;
import me.devking2106.useddeal.entity.User;

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

	private CommonStatus priceSuggestYN;

	@NotBlank
	private String locationName;



	public Board toEntity(Long userId, String locationName, Long locationId, Double latitude, Double longitude) {
		priceSuggestYN = Objects.requireNonNullElse(priceSuggestYN, CommonStatus.N);
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
			.priceSuggestYN(priceSuggestYN)
			.pullYN(CommonStatus.N)
			.viewCount(0L)
			.latitude(latitude)
			.longitude(longitude)
			.locationName(locationName)
			.build();
	}

}
