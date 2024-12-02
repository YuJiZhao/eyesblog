package com.eyes.eyesspace.utils;

import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

/**
 * 操作系统工具类
 *
 * @author eyes
 */
public class OSUtils {
	private OSUtils() {
		throw new UnsupportedOperationException("It is not recommended to instantiate this class. It is recommended to use static method calls");
	}

	/**
	 * 获取操作系统名称
	 *
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static String osName(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		UserAgent ua = UserAgent.parseUserAgentString(userAgent);
		OperatingSystem os = ua.getOperatingSystem();
		return os.getName();
	}
}
