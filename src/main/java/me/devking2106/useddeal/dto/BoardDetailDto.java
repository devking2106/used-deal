package me.devking2106.useddeal.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.devking2106.useddeal.entity.Board;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailDto {

	private Long id;
	private Long userId;
	private String nickname;
	private String title;
	private String content;
	private String locationName;
	private Long price;
	private Long categoryId;
	private Board.Status status;
	private LocalDateTime boardDate;
	private boolean isPull;

	public BoardDetailDto toEntity(Long userId, String locationName, Long locationId, double latitude,
		double longitude) {
		return BoardDetailDto.builder()
			.id(id)
			.userId(userId)
			.nickname(nickname)
			.title(title)
			.content(content)
			.locationName(locationName)
			.price(price)
			.categoryId(categoryId)
			.status(status)
			.boardDate(boardDate)
			.isPull(isPull)
			.build();
	}

}
