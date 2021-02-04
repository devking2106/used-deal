package me.devking2106.useddeal.controller.results;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.devking2106.useddeal.controller.response.FieldErrorDetail;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationResult {

	List<FieldErrorDetail> errors;

	public static ValidationResult create(Errors errors, MessageSource messageSource, Locale locale) {
		List<FieldErrorDetail> details = errors.getFieldErrors()
			.stream()
			.map(error -> FieldErrorDetail.create(error, messageSource, locale))
			.collect(Collectors.toList());
		return new ValidationResult(details);
	}

}
