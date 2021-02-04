package me.devking2106.useddeal.controller.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.StatusException;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	private String message;
	private int status;
	private List<FieldErrorDetail> errors;

	private ErrorResponse(StatusException statusException, List<FieldErrorDetail> errors) {
		this.message = statusException.getMessage();
		this.status = statusException.getStatus();
		this.errors = errors;
	}

	private ErrorResponse(StatusException statusException) {
		this.message = statusException.getMessage();
		this.status = statusException.getStatus();
		this.errors = new ArrayList<>();
	}

	private ErrorResponse(ExceptionStatus exceptionStatus, List<FieldErrorDetail> errors) {
		this.message = exceptionStatus.getMessage();
		this.status = exceptionStatus.getStatus();
		this.errors = errors;
	}

	public static ErrorResponse of(ExceptionStatus code, BindingResult bindingResult) {
		return new ErrorResponse(code, FieldErrorDetail.of(bindingResult));
	}

	public static ErrorResponse of(StatusException statusException) {
		return new ErrorResponse(statusException);
	}

	public static ErrorResponse of(StatusException statusException, List<FieldErrorDetail> errors) {
		return new ErrorResponse(statusException, errors);
	}

	public static ErrorResponse of(MethodArgumentTypeMismatchException ex) {
		String value = ex.getValue() == null ? "" : ex.getValue().toString();
		List<FieldErrorDetail> errors = FieldErrorDetail.of(ex.getName(), value, ex.getErrorCode());
		return new ErrorResponse(ExceptionStatus.INVALID_TYPE_VALUE_EXCEPTION, errors);
	}

	public static ErrorResponse of(InvalidFormatException ex) {
		List<FieldErrorDetail> errors = FieldErrorDetail.of(ex.getPath().get(0).getFieldName(), ex.getValue().toString(),
			ex.getTargetType().toString());
		return new ErrorResponse(ExceptionStatus.INVALID_FORMAT_EXCEPTION, errors);
	}

	public static ErrorResponse of(HttpRequestMethodNotSupportedException ex) {
		String supportedMethods = Arrays.stream(Objects.requireNonNull(ex.getSupportedMethods()))
			.map(String::toString)
			.collect(Collectors.joining(", "));
		List<FieldErrorDetail> details = FieldErrorDetail.of(ex.getLocalizedMessage(), "입력한 HTTP Method = " + ex.getMethod(),
			"지원 가능한 HTTP Method = " + supportedMethods);
		return new ErrorResponse(ExceptionStatus.METHOD_NOT_ALLOWED, details);
	}
}
