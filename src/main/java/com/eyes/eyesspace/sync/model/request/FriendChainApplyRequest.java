package com.eyes.eyesspace.sync.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author eyesYeager
 * date 2023/5/31 15:54
 */

@Data
public class FriendChainApplyRequest {
	@NotBlank(message = "网站名称不能为空")
	@Length(min = 2, max = 15, message = "网站名称长度需要在2-15之间")
	private String name;

	@NotBlank(message = "网站介绍不能为空")
	@Length(max = 40, message = "网站介绍长度应不大于40")
	private String introduce;

	@NotBlank(message = "头像链接不能为空")
	@Length(max = 100, message = "头像链接长度应不大于100")
	private String avatar;

	@NotBlank(message = "网站地址不能为空")
	@Length(max = 100, message = "网站地址长度应不大于100")
	private String address;
}
