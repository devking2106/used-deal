package me.devking2106.useddeal.controller.results;
import java.util.HashMap;

import org.springframework.util.Assert;

/**
 * ApiResult 상태 코드 + 설정 전략
 */
public class ApiResult extends HashMap<String, Object> {

	private static final long serialVersionUID = 877825499039674411L;

	private static final String MESSAGE_KEY = "message";
	private static final String ERROR_CODE_KEY = "errorReferenceCode";

	public static ApiResult blank() {
		return new ApiResult();
	}

	public static ApiResult message(String message) {
		Assert.hasText(message, "Parameter `message` 비어있으면 안됩니다");

		ApiResult apiResult = new ApiResult();
		apiResult.put("message", message);
		return apiResult;
	}

	public static ApiResult error(String message, String errorReferenceCode) {
		Assert.hasText(message, "Parameter `message` 비어있으면 안됩니다");
		Assert.hasText(errorReferenceCode, "Parameter `errorReferenceCode` 비어있으면 안됩니다");

		ApiResult apiResult = new ApiResult();
		apiResult.put(MESSAGE_KEY, message);
		apiResult.put(ERROR_CODE_KEY, errorReferenceCode);
		return apiResult;
	}

	public ApiResult add(String key, Object value) {
		Assert.hasText(key, "Parameter `key` 가 비어있으면 안됩니다");
		Assert.notNull(value, "Parameter `value` 가 null 이면 안됩니다");

		this.put(key, value);
		return this;
	}
}
