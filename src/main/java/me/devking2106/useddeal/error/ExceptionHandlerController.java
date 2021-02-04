package me.devking2106.useddeal.error;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		final ErrorResponse response = ErrorResponse.of(ExceptionStatus.INVALID_INPUT_VALUE, exception.getBindingResult());
		errorLogging(exception);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}


	/**
	 * @RequestParam enum type 불일치로 binding 못할 때
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> badRequestExceptions(MethodArgumentTypeMismatchException exception) {
		final ErrorResponse response = ErrorResponse.of(exception);
		errorLogging(exception);
		return new ResponseEntity<>(response ,HttpStatus.BAD_REQUEST);
	}

	/**
	 * @ModelAttribute 나 RequestBody 로 @Valid 로 Binding 못할 때
	 */
	@ExceptionHandler(BindException.class)
	protected ResponseEntity<ErrorResponse> handleBindException(BindException exception) {
		final ErrorResponse errorResponse = ErrorResponse.of(ExceptionStatus.INVALID_TYPE_VALUE_EXCEPTION, exception.getBindingResult());
		errorLogging(exception);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 지원하지 않는 메소드를 요청 했을때
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
		final ErrorResponse response = ErrorResponse.of(exception);
		return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
	}


	@ExceptionHandler(StatusException.class)
	protected ResponseEntity<ErrorResponse> statusExceptionHandler(StatusException exception) {
		final ErrorResponse response = ErrorResponse.of(exception);
		errorLogging(exception);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * RequestBody 에 잘못된 타입을 보냈을 때
	 */
	@ExceptionHandler(InvalidFormatException.class)
	protected ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException exception) {
		final ErrorResponse response = ErrorResponse.of(exception);
		errorLogging(exception);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoSuchElementException.class)
	protected ResponseEntity<?> handleNoSuchElementException(NoSuchElementException exception) {
		errorLogging(exception);
		return new ResponseEntity<>("검색 결과가 없습니다", HttpStatus.BAD_REQUEST);
	}




	private void errorLogging(Exception exception) {
		log.error("Exception = {} , message = {}", exception.getClass().getSimpleName(), exception.getLocalizedMessage());
	}

}
