package com.eyes.eyesspace.utils.email;

import lombok.Data;

/**
 * 接收邮件实体类
 *
 * @author eyes
 * date 2023/1/9 10:05
 */
@Data
public class ImapEmailInfo {
	private String subject;

	private String sender;

	private String content;
}
