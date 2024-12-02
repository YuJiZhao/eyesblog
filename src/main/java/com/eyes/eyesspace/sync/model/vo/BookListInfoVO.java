package com.eyes.eyesspace.sync.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author artonyu
 * date 2024-08-01 10:01
 */

@Data
public class BookListInfoVO {
	private Long totalNum;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long publicNum;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long privateNum;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long deleteNum;

	private Integer clickNum;

	private Integer commentNum;

	public BookListInfoVO(Integer clickNum, Integer commentNum) {
		this.clickNum = clickNum;
		this.commentNum = commentNum;
	}
}
