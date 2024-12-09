package com.eyes.eyesspace.model.request;

import com.eyes.eyesspace.constant.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ShuoAddRequest {
	@JsonIgnore
	private Integer id;

	@NotNull(message = "说说内容不能为空")
	private String content;

	private Integer status = StatusEnum.PUBLIC.getStatus();

	private List<String> picList;
}
