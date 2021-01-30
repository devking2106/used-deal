package me.devking2106.useddeal.error;

import java.util.Locale;
import java.util.NoSuchElementException;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.devking2106.useddeal.controller.results.ApiResult;
import me.devking2106.useddeal.controller.results.Result;
import me.devking2106.useddeal.controller.results.ValidationResult;
import me.devking2106.useddeal.error.exception.common.StatusException;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {

	private final MessageSource messageSource;


	private static String getSimpleName(Exception exception) {
		return exception.getClass().getSimpleName();
	}

	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResult> badRequestExceptions(Exception exception) {
		return Result.failure("요청한 데이터의 유형이 올바르지 않습니다. 입력 값을 확인해주세요");
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ValidationResult> handleBindException(BindException bindException, Locale locale) {
		ValidationResult validationResult = ValidationResult.create(bindException, messageSource, locale);
		return Result.failure(validationResult);
	}

	@ExceptionHandler(StatusException.class)
	public ResponseEntity<ApiResult> statusExceptionHandler(StatusException statusException) {
		log.error("throw Exception Message= {}", statusException.getMessage());
		return Result.failure(statusException);
	}

	@ExceptionHandler(InvalidFormatException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResult> handleInvalidFormatException(InvalidFormatException invalidFormatException) {
		return Result.failure(invalidFormatException);
	}

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResult> handleNoSuchElementException(NoSuchElementException noSuchElementException) {
		return Result.failure("검색 결과가 없습니다.");
	}

}
