package com.eyes.eyesspace.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShuoInfoDTO {
	private Integer id;

	private String content;

	private List<String> picList;

	private Integer status;

	private String createTime;
}