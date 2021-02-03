package me.devking2106.useddeal.controller.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.devking2106.useddeal.entity.Board;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardRequest {

	@Getter
	@Setter
	@Builder
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Find {

		private Board.FilterType filterType;

		private String query;

		@NotNull
		private String location;

		@Range(min = 0, max = 15)
		private int range;
	}

}
