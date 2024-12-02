package com.eyes.eyesspace.sync.model.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/6/1 15:24
 */

@Data
public class FriendChainRefuseRequest {
	@NotNull(message = "id不能为空")
	private Integer id;

	private String notes = "暂无";

	@NotNull(message = "needDelete不能为空")
	private Boolean needDelete;
}
