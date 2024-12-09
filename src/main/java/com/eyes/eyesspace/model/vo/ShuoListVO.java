package com.eyes.eyesspace.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import lombok.Data;

@Data
public class ShuoListVO {
	private String content;

	private List<String> picList;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer status;

	private String createTime;
}
