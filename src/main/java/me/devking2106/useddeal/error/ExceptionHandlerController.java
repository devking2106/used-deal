package me.devking2106.useddeal.error;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.extern.log4j.Log4j2;
import me.devking2106.useddeal.controller.response.ErrorResponse;
import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.StatusException;

@Log4j2
@RestControllerAdvice
public class ExceptionHandlerController {

	/**
	 * @Valid,@Validated 검증으로 binding 못할 때
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		final ErrorResponse response = ErrorResponse.of(ExceptionStatus.INVALID_INPUT_VALUE,
			exception.getBindingResult());
		errorLogging(exception);
		return response;
	}

	/**
	 * @RequestParam enum type 불일치로 binding 못할 때
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	private ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
		final ErrorResponse response = ErrorResponse.of(exception);
		errorLogging(exception);
		return response;
	}

	/**
	 * @ModelAttribute 나 RequestBody 로 @Valid 로 Binding 못할 때
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	private ErrorResponse handleBindException(BindException exception) {
		final ErrorResponse response = ErrorResponse.of(ExceptionStatus.INVALID_TYPE_VALUE_EXCEPTION,
			exception.getBindingResult());
		errorLogging(exception);
		return response;
	}

	/**
	 * 지원하지 않는 HTTP METHOD 를 요청 했을때
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	private ErrorResponse handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException exception) {
		final ErrorResponse response = ErrorResponse.of(exception);
		return response;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(StatusException.class)
	private ErrorResponse handleStatusException(StatusException exception) {
		final ErrorResponse response = ErrorResponse.of(exception);
		errorLogging(exception);
		return response;
	}

	/**
	 * @RequestBody 에 잘못된 타입을 보냈을 때
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidFormatException.class)
	private ErrorResponse handleInvalidFormatException(InvalidFormatException exception) {
		final ErrorResponse response = ErrorResponse.of(exception);
		errorLogging(exception);
		return response;
	}

	private void errorLogging(Exception ex) {
		log.error("Exception = {} , message = {}", ex.getClass().getSimpleName(),
			ex.getLocalizedMessage());
	}

}
