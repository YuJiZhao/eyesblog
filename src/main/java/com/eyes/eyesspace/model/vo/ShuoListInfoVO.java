package com.eyes.eyesspace.model.vo;

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

	public ShuoListInfoVO(Integer totalNum) {
		this.totalNum = totalNum;
	}
}
