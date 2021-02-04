package me.devking2106.useddeal.controller.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldErrorDetail {

	private final String field;
	private final String value;
	private final String reason;

	public static List<FieldErrorDetail> of(String field, String value, String reason) {
		List<FieldErrorDetail> fieldErrors = new ArrayList<>();
		fieldErrors.add(new FieldErrorDetail(field, value, reason));
		return fieldErrors;
	}

	public static List<FieldErrorDetail> of(BindingResult bindingResult) {
		final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		return fieldErrors.stream()
			.map(error -> new FieldErrorDetail(
				error.getField(),
				error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
				error.getDefaultMessage() == null ? "" :
					error.getDefaultMessage().contains("No enum") ? "Enum Type이 일치하지 않습니다." :
						error.getDefaultMessage()))
			.collect(Collectors.toList());
	}

}

