package com.eyes.eyesspace.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口返回值封装
 *
 * @param <V>
 * @author eyes
 */
@Data
@NoArgsConstructor
public class Result<V> {
	private Integer code;

	private String msg;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private V data;

	public Result(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Result(Integer code, String msg, V data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static Result<Void> success() {
		return new Result<>(
				DefaultResultCode.SUCCESS.getCode(),
				DefaultResultCode.SUCCESS.getMessage()
		);
	}

	public static Result<Void> success(String msg) {
		return new Result<>(DefaultResultCode.SUCCESS.getCode(), msg);
	}

	public static <T> Result<T> success(T data) {
		return new Result<>(
				DefaultResultCode.SUCCESS.getCode(),
				DefaultResultCode.SUCCESS.getMessage(),
				data
		);
	}

	public static <T> Result<T> success(String msg, T data) {
		return new Result<>(DefaultResultCode.SUCCESS.getCode(), msg, data);
	}

	public static <T> Result<T> success(Integer code, String msg, T data) {
		return new Result<>(code, msg, data);
	}

	public static Result<Void> fail() {
		return new Result<>(DefaultResultCode.FAILURE.getCode(), DefaultResultCode.FAILURE.getMessage());
	}

	public static Result<Void> fail(String msg) {
		return new Result<>(DefaultResultCode.FAILURE.getCode(), msg);
	}

	public static Result<Void> fail(Integer code, String msg) {
		return new Result<>(code, msg);
	}
}
