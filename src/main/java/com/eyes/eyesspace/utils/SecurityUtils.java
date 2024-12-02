package com.eyes.eyesspace.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

/**
 * @author eyesYeager
 * data 2024/12/1 15:04
 */

@Slf4j
@Component
public class SecurityUtils {

	@Value("${security.hash-algorithm}")
	private String hashAlgorithm;

	@Value("${security.hash-num}")
	private Integer hashNum;

	@Value("${security.symmetric-algorithm}")
	private String symmetricAlgorithm;

	@Value("${security.symmetric-key}")
	private String symmetricKey;

	@Value("${security.front-iv}")
	private String frontIv;

	@Value("${security.front-key}")
	private String frontKey;

	/*
	 *****************************************************************************************
	 *                                      单向散列加密
	 *****************************************************************************************
	 */

	/**
	 * 单向加密
	 *
	 * @param str 待加密字符串
	 * @return 加密字符串
	 */
	public String oneWayHash(String str) {
		return oneWayHash(str, hashAlgorithm, hashNum);
	}

	/**
	 * 单向加密
	 *
	 * @param str       待加密字符串
	 * @param algorithm 加密算法
	 * @return 加密字符串
	 */
	public String onWayHash(String str, String algorithm) {
		return oneWayHash(str, algorithm, hashNum);
	}

	/**
	 * 单向加密
	 *
	 * @param str   待加密字符串
	 * @param times 加密次数
	 * @return 加密字符串
	 */
	public String onWayHash(String str, int times) {
		return oneWayHash(str, hashAlgorithm, times);
	}

	/**
	 * 单向加密
	 *
	 * @param str       待加密字符串
	 * @param algorithm 可选值为MD5，SHA-1等9种算法，详情见下面链接
	 * @param times     加密次数
	 * @return 加密字符串
	 * @link https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#MessageDigest
	 */
	public static String oneWayHash(String str, String algorithm, int times) {
		if (times <= 0) {
			throw new IllegalArgumentException("Encryption times must be greater than 0");
		}
		if (times > 100) {
			log.warn("Too many encryption times can easily lead to performance degradation");
		}
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] srcBytes = str.getBytes();
			for (int i = 0; i < times; i++) {
				md.update(srcBytes);
				srcBytes = md.digest();
			}
			return Base64.getEncoder().encodeToString(srcBytes);
		} catch (NoSuchAlgorithmException e) {
			log.error("The encryption algorithm does not exist. See @link https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#MessageDigest for details");
			return null;
		}
	}

	/*
	 *****************************************************************************************
	 *                                      双向加密
	 *****************************************************************************************
	 */

	/**
	 * 对称加密字符串
	 *
	 * @param str 待加密字符串
	 * @return 加密字符串
	 */
	public String symmetricEncrypt(String str)
			throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
		return symmetricEncrypt(str, symmetricAlgorithm);
	}

	/**
	 * 对称加密字符串
	 *
	 * @param str       待加密字符串
	 * @param algorithm 加密算法
	 * @return 加密字符串
	 */
	public String symmetricEncrypt(String str, String algorithm)
			throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
		SecretKeySpec key = new SecretKeySpec(symmetricKey.getBytes(), algorithm);
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encipherByte = cipher.doFinal(str.getBytes());
		return Base64.getEncoder().encodeToString(encipherByte);
	}

	/**
	 * 对称解密
	 *
	 * @param str 待解密字符串
	 * @return 原始字符串
	 */
	public String symmetricDecrypt(String str)
			throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
		return symmetricDecrypt(str, symmetricAlgorithm);
	}

	/**
	 * 对称解密
	 *
	 * @param str       待解密字符串
	 * @param algorithm 加密算法
	 * @return 原始字符串
	 */
	public String symmetricDecrypt(String str, String algorithm)
			throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
		SecretKeySpec key = new SecretKeySpec(symmetricKey.getBytes(), algorithm);
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decode = Base64.getDecoder().decode(str);
		byte[] decipherByte = cipher.doFinal(decode);
		return new String(decipherByte);
	}

	/**
	 * 打乱字符串顺序
	 *
	 * @param str 待处理字符串
	 * @return 乱序字符串
	 */
	public static String disorderStr(String str) {
		String s = swapStr(str);
		return new StringBuilder(s).reverse().toString();
	}

	/**
	 * 恢复被打乱的字符串
	 *
	 * @param str 乱序字符串
	 * @return 原始字符串
	 */
	public static String collateStr(String str) {
		String s = new StringBuilder(str).reverse().toString();
		return swapStr(s);
	}

	/**
	 * 字符串相邻字符两两交换
	 */
	private static String swapStr(String str) {
		if (str.length() <= 1) return str;
		char[] chars = str.toCharArray();
		for (int i = 1; i < chars.length; i += 2) {
			char t = chars[i - 1];
			chars[i - 1] = chars[i];
			chars[i] = t;
		}
		return String.valueOf(chars);
	}

	/*
	 *****************************************************************************************
	 *                                     其他业务工具
	 *****************************************************************************************
	 */

	/**
	 * AES解密前端数据
	 *
	 * @param cipherStr 待解密字符串
	 * @return 原始数据
	 */
	public String decryptFront(String cipherStr)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// 对于前端的密文要先base64解密
		IvParameterSpec iv = new IvParameterSpec(frontIv.getBytes());
		SecretKeySpec secretKey = new SecretKeySpec(frontKey.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		return new String(
				cipher.doFinal(Base64.getDecoder().decode(cipherStr)),
				StandardCharsets.UTF_8
		);
	}

	/**
	 * SSL绕过验证
	 *
	 * @return SSLContext
	 */
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {

		SSLContext sc = SSLContext.getInstance("SSLv3");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[]{trustManager}, null);
		return sc;
	}
}