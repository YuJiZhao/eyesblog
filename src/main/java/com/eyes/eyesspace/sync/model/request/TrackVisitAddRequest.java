package com.eyes.eyesspace.sync.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TrackVisitAddRequest {
	@NotNull(message = "入口路径不能为空")
	private String path;
}
