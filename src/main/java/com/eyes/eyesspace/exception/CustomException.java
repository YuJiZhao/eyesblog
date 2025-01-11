package com.eyes.eyesspace.exception;

import com.eyes.eyesspace.result.DefaultResultCode;
import com.eyes.eyesspace.result.ResultCodeInterface;
import lombok.Getter;
import lombok.ToString;

/**
 * 业务异常类
 *
 * @author eyes
 */
@ToString
@Getter
public class CustomException extends Exception {
	protected Integer errorCode;

	protected String errorMsg;

	public CustomException() {
		this.errorCode = DefaultResultCode.FAILURE.getCode();
		this.errorMsg = DefaultResultCode.FAILURE.getMessage();
	}

	public CustomException(String errorMsg) {
		this.errorCode = DefaultResultCode.FAILURE.getCode();
		this.errorMsg = errorMsg;
	}

	public CustomException(Integer errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public CustomException(ResultCodeInterface resultCode) {
		this.errorCode = resultCode.getCode();
		this.errorMsg = resultCode.getMessage();
	}

	public CustomException(Exception e) {
		super(e);
	}

	public CustomException(String errorMsg, Exception e) {
		super(errorMsg, e);
	}
}
