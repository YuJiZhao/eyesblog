package com.eyes.eyesspace.model.request;

import com.eyes.eyesspace.constant.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class VideoAddRequest {
	@JsonIgnore
	private Integer id;

	@NotNull(message = "视频标题不能为空")
	private String title;

	private String originalAuthor = "佚名";

	@NotNull(message = "视频封面不能为空")
	private String pictureUrl;

	@NotNull(message = "视频文件不能为空")
	private String videoUrl;

	@NotNull(message = "转载地址不能玩为空")
	private String originalUrl;

	private String ownerComment = "暂无";

	private Integer status = StatusEnum.PUBLIC.getStatus();
}
