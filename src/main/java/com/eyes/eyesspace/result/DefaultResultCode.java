package com.eyes.eyesspace.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 默认状态码
 *
 * @author eyesYeager
 */
@Getter
@AllArgsConstructor
public enum DefaultResultCode implements ResultCodeInterface {
	SUCCESS(200, "success"),

	FAILURE(400, "fail");

	private final Integer code;

	private final String message;
}