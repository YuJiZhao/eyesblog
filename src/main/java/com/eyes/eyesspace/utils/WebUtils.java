package com.eyes.eyesspace.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * Web服务工具类
 *
 * @author eyes
 */
public class WebUtils {
	private WebUtils() {
		throw new UnsupportedOperationException("It is not recommended to instantiate this class. It is recommended to use static method calls");
	}

	/**
	 * 获取HttpServletRequest
	 *
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (Objects.isNull(requestAttributes)) {
			return null;
		}
		return ((ServletRequestAttributes) requestAttributes).getRequest();
	}

	/**
	 * 获取HttpServletResponse
	 *
	 * @return HttpServletResponse
	 */
	public static HttpServletResponse getResponse() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (Objects.isNull(requestAttributes)) {
			return null;
		}
		return ((ServletRequestAttributes) requestAttributes).getResponse();
	}

	/**
	 * 添加响应头
	 *
	 * @param key   String
	 * @param value String
	 * @return boolean
	 */
	public static boolean setResponse(String key, String value) {
		HttpServletResponse response;
		if (Objects.isNull(response = getResponse())) {
			return false;
		}
		response.addHeader(key, value);
		return true;
	}

	/**
	 * 批量添加响应头
	 *
	 * @param map Map<String, String>
	 * @return boolean
	 */
	public static boolean setResponse(Map<String, String> map) {
		HttpServletResponse response;
		if (Objects.isNull(response = getResponse())) {
			return false;
		}
		for (Map.Entry<String, String> entry : map.entrySet()) {
			response.addHeader(entry.getKey(), entry.getValue());
		}
		return true;
	}
}
