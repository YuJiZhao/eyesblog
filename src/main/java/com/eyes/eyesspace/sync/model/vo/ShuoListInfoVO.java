package com.eyes.eyesspace.sync.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShuoListInfoVO {
	private Integer totalNum;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer publicNum;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer privateNum;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer deleteNum;

	private Integer viewsNum;

	private Integer commentsNum;

	public ShuoListInfoVO(Integer totalNum, Integer viewsNum, Integer commentsNum) {
		this.totalNum = totalNum;
		this.viewsNum = viewsNum;
		this.commentsNum = commentsNum;
	}
}
