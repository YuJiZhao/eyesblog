package com.eyes.eyesspace.sync.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/6/1 11:13
 */

@Data
@AllArgsConstructor
public class FriendListInfoVO {
	private Long validChain;

	private Long invalidChain;

	private Long verifyingChain;
}
