package me.devking2106.useddeal.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.devking2106.useddeal.entity.Board;
import me.devking2106.useddeal.error.exception.board.BoardStatusHideException;

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
		if (Board.Status.HIDE_CANCEL.equals(status) && !this.status.equals(Board.Status.HIDE)) {
			return false;
		}
		return !this.status.equals(status);
	}

	public void boardNotHideAndMyBoard(long userId) {
		if (this.userId != userId && this.status == Board.Status.HIDE) {
			throw new BoardStatusHideException();
		}
	}
}
