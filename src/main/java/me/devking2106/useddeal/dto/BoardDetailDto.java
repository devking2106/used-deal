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
		// status 가 HIDE 일때 HIDE_CANCEL(패스) true 아닐때 HIDE
		if (status == Board.Status.HIDE) {
			return true;
		}
		// HIDE를 제외하고 boardDetailDto가 status와 같을 시 false
		return !(this.status == status);
		// if (Board.Status.HIDE_CANCEL.equals(status) && !this.status.equals(Board.Status.HIDE)) {
		// 	return false;
		// }
		// return !this.status.equals(status);
	}

	public boolean isBoardNotHideAndMyBoard(long userId) {
		return this.userId != userId && this.status == Board.Status.HIDE;
	}
}
