package com.eyes.eyesspace.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author eyesYeager
 * data 2024/12/8 17:48
 */

public class RandomUtils {
	private static final Random RANDOM = new Random();

	private static final String DEFAULT_STR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private RandomUtils() {
		throw new UnsupportedOperationException("It is not recommended to instantiate this class. It is recommended to use static method calls");
	}

	public static String getRandomStr(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int number = RANDOM.nextInt(DEFAULT_STR.length());
			sb.append(DEFAULT_STR.charAt(number));
		}
		return sb.toString();
	}

	public static String getUUid() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}
}
