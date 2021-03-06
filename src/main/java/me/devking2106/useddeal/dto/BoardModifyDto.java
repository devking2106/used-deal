package me.devking2106.useddeal.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardModifyDto {

	@NotBlank
	private String title;

	@NotBlank
	private String content;

	@Min(0)
	private long price;

	@NotNull
	@Min(0)
	private Long categoryId;

}
