package com.eyes.eyesspace.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据脱敏工具类
 *
 * @author eyesYeager
 * date 2023/2/10 14:40
 */

public class SensitiveInfoUtils {
	/**
	 * 中文姓名脱敏
	 * 只显示第一个汉字，其他隐藏为星号
	 *
	 * @param fullName 姓名
	 * @return 脱敏中文姓名(例 ： 李 * *)
	 */
	public static String chineseName(String fullName) {
		if (StringUtils.isBlank(fullName)) {
			return "";
		}
		String name = StringUtils.left(fullName, 1);
		return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
	}

	/**
	 * 中文姓名脱敏
	 * 只显示姓氏，其他隐藏为星号
	 *
	 * @param familyName 姓氏
	 * @param givenName  名字
	 * @return 脱敏中文姓名(例 ： 欧阳 * *)
	 */
	public static String chineseName(String familyName, String givenName) {
		if (StringUtils.isBlank(familyName) || StringUtils.isBlank(givenName)) {
			return "";
		}
		if (familyName.length() > 1) {
			String name = StringUtils.left(familyName, familyName.length());
			return StringUtils.rightPad(name, StringUtils.length(familyName + givenName), "*");
		}
		return chineseName(familyName + givenName);
	}

	/**
	 * 身份证号脱敏
	 * 显示最后四位，其他隐藏。共计18位或者15位
	 *
	 * @param id 身份证号
	 * @return 脱敏身份证号(例 ： * * * * * * * * * * * * * 5762)
	 */
	public static String idCardNum(String id) {
		if (StringUtils.isBlank(id)) {
			return "";
		}
		String num = StringUtils.right(id, 4);
		return StringUtils.leftPad(num, StringUtils.length(id), "*");
	}


	/**
	 * 身份证号脱敏
	 * 显示前六位与后四位，其他用星号隐藏
	 *
	 * @param carId 身份证号
	 * @return 脱敏身份证号(例 ： 451002 * * * * * * * * 1647)
	 */
	public static String idCard(String carId) {
		if (StringUtils.isBlank(carId)) {
			return "";
		}
		return StringUtils.left(carId, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(carId, 4), StringUtils.length(carId), "*"), "******"));
	}

	/**
	 * 固定电话脱敏
	 * 只展示后四位，其他隐藏
	 *
	 * @param num 固定电话
	 * @return 脱敏固定电话(例 ： * * * * 1234)
	 */
	public static String fixedPhone(String num) {
		if (StringUtils.isBlank(num)) {
			return "";
		}
		return StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*");
	}

	/**
	 * 手机号码脱敏
	 * 只展示前三位，后四位，其他隐藏
	 *
	 * @param num 手机号码
	 * @return 脱敏手机号(例 ： 138 * * * * * * 1234)
	 */
	public static String mobilePhone(String num) {
		if (StringUtils.isBlank(num)) {
			return "";
		}
		return StringUtils.left(num, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*"), "***"));
	}

	/**
	 * 地址脱敏 只显示到地区，不显示详细地址
	 *
	 * @param address       地址
	 * @param sensitiveSize 敏感信息长度
	 * @return 脱敏地址(例 ： 北京市海淀区 * * * *)
	 */
	public static String address(String address, int sensitiveSize) {
		if (StringUtils.isBlank(address)) {
			return "";
		}
		int length = StringUtils.length(address);
		return StringUtils.rightPad(StringUtils.left(address, length - sensitiveSize), length, "*");
	}

	/**
	 * 电子邮箱脱敏
	 * 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示
	 *
	 * @param email 电子邮箱
	 * @return 脱敏邮箱(例子 ： g * * @ 163.com)
	 */
	public static String email(String email) {
		if (StringUtils.isBlank(email)) {
			return "";
		}
		int index = StringUtils.indexOf(email, "@");
		if (index <= 1)
			return email;
		else
			return StringUtils.rightPad(StringUtils.left(email, 1), index, "*").concat(StringUtils.mid(email, index, StringUtils.length(email)));
	}

	/**
	 * 银行卡号脱敏
	 * 前六位，后四位，其他用星号隐藏
	 *
	 * @param cardNum 银行卡号
	 * @return 脱敏银行卡号(例 ： 6222600 * * * * * * * * * * 1234)
	 */
	public static String bankCard(String cardNum) {
		if (StringUtils.isBlank(cardNum)) {
			return "";
		}
		return StringUtils.left(cardNum, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(cardNum, 4), StringUtils.length(cardNum), "*"), "******"));
	}

	/**
	 * 公司开户银行联号脱敏
	 * 显示前两位，其他用星号隐藏
	 *
	 * @param code 公司开户银行联号
	 * @return 脱敏银行联号(例 : 12 * * * * * * * *)
	 */
	public static String cnapsCode(String code) {
		if (StringUtils.isBlank(code)) {
			return "";
		}
		return StringUtils.rightPad(StringUtils.left(code, 2), StringUtils.length(code), "*");
	}
}