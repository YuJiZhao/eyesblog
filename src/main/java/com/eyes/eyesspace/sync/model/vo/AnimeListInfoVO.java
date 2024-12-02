package com.eyes.eyesspace.sync.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/5/22 14:44
 */

@Data
public class AnimeListInfoVO {
	private Long totalNum;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long publicNum;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long privateNum;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long deleteNum;

	private Integer clickNum;

	private Integer commentNum;

	public AnimeListInfoVO(Integer clickNum, Integer commentNum) {
		this.clickNum = clickNum;
		this.commentNum = commentNum;
	}
}
