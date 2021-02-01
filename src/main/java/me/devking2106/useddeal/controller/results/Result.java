package me.devking2106.useddeal.controller.results;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Result {

	public static ResponseEntity<ApiResult> created() {
		return ResponseEntity.status(201).build();
	}

	public static ResponseEntity<ApiResult> created(ApiResult payload) {
		Assert.notNull(payload, "Parameter `payload` 가 널이면 안됩니다");
		return ResponseEntity.status(201).body(payload);
	}

	public static ResponseEntity<ApiResult> ok(String message) {
		Assert.hasText(message, "Parameter `message` 가 비어있으면 안됩니다");
		return ok(ApiResult.message(message));
	}

	public static ResponseEntity<ApiResult> ok(ApiResult payload) {
		Assert.notNull(payload, "Parameter `payload` 가 널이면 안됩니다");
		return ResponseEntity.ok(payload);
	}

	public static ResponseEntity<ApiResult> failure(InvalidFormatException invalidFormatException) {
		ApiResult apiResult = new ApiResult();
		apiResult.add("message", "지원하지 않는 포맷 형식입니다. type 변경해주세요");
		apiResult.add("fieldName", invalidFormatException.getPath().get(0).getFieldName());
		apiResult.add("inputValue", invalidFormatException.getValue());
		apiResult.add("type", invalidFormatException.getTargetType());
		apiResult.add("status", 400);
		return ResponseEntity.badRequest().body(apiResult);
	}

	public static ResponseEntity<ApiResult> failure(ApiResult payload) {
		return ResponseEntity.badRequest().body(payload);
	}

	public static ResponseEntity<ApiResult> failure(String message) {
		return ResponseEntity.badRequest().body(ApiResult.message(message));
	}

	public static ResponseEntity<ValidationResult> failure(ValidationResult validationResult) {
		return ResponseEntity.badRequest().body(validationResult);
	}

	// public static ResponseEntity<ApiResult> failure(StatusException ex) {
	// 	ApiResult apiResult =  new ApiResult();
	// 	apiResult.add("message", ex.getMessage());
	// 	apiResult.add("status", ex.getStatus());
	// 	return ResponseEntity.badRequest().body(apiResult);
	// }

	public static ResponseEntity<ApiResult> serverError(String message, String errorReferenceCode) {
		return ResponseEntity.status(500).body(ApiResult.error(message, errorReferenceCode));
	}

	public static ResponseEntity<ApiResult> notFound() {
		return ResponseEntity.notFound().build();
	}

	public static ResponseEntity<ApiResult> unauthenticated() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	public static ResponseEntity<ApiResult> forbidden() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

}
