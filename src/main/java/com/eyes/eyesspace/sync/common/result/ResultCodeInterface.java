package com.eyes.eyesspace.sync.common.result;

/**
 * 状态码接口
 * 状态码类均需实现该接口，否则无法作为参数传入Result
 *
 * @author eyes
 * date 2023/1/13 10:20
 */

public interface ResultCodeInterface {
	Integer getCode();

	String getMessage();
}
