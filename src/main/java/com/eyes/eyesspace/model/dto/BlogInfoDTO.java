package com.eyes.eyesspace.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BlogInfoDTO {
	private String title;

	private String summary;

	private String content;

	private String category;

	private List<String> labels;

	private Integer views;

	private Integer words;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer status;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;
}
