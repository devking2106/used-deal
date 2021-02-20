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

	public boolean isStatusUpdatable(Board.Status status) {
		if (status == Board.Status.HIDE) {
			return true;
		}
		return !(this.status == status);
	}

	public boolean isBoardNotHideAndMyBoard(long userId) {
		return this.userId != userId && this.status == Board.Status.HIDE;
	}
}
