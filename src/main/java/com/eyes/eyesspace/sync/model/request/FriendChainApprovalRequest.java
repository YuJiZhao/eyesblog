package com.eyes.eyesspace.sync.model.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/6/1 14:08
 */

@Data
public class FriendChainApprovalRequest {
	@NotNull(message = "友链id不能为空")
	private Integer id;

	private String notes = "暂无";
}
