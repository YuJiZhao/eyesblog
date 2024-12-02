package com.eyes.eyesspace.sync.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import lombok.Data;

@Data
public class ShuoListVO {
	private String id;

	private String content;

	private List<String> picList;

	private Integer views;

	private Integer comments;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer status;

	private String createTime;
}
