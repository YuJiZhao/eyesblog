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
public class BizException extends Exception {
	protected Integer errorCode;

	protected String errorMsg;

	public BizException() {
		this.errorCode = DefaultResultCode.FAILURE.getCode();
		this.errorMsg = DefaultResultCode.FAILURE.getMessage();
	}

	public BizException(String errorMsg) {
		this.errorCode = DefaultResultCode.FAILURE.getCode();
		this.errorMsg = errorMsg;
	}

	public BizException(Integer errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public BizException(ResultCodeInterface resultCode) {
		this.errorCode = resultCode.getCode();
		this.errorMsg = resultCode.getMessage();
	}

	public BizException(Exception e) {
		super(e);
	}

	public BizException(String errorMsg, Exception e) {
		super(errorMsg, e);
	}
}
