package me.devking2106.useddeal.controller.response;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldErrorDetail {

	String field;
	String message;
	public static FieldErrorDetail create(FieldError fieldError, MessageSource messageSource, Locale locale) {
		return new FieldErrorDetail(
			fieldError.getField(),
			messageSource.getMessage(fieldError, locale));
	}

}

