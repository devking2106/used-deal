package me.devking2106.useddeal.controller.response;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class FieldErrorDetail {

	private final String field;
	private final String message;
	public static FieldErrorDetail create(FieldError fieldError, MessageSource messageSource, Locale locale) {
		return new FieldErrorDetail(
			fieldError.getField(),
			messageSource.getMessage(fieldError, locale));
	}

}

