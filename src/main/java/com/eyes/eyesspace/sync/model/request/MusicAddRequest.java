package com.eyes.eyesspace.sync.model.request;

import com.eyes.eyesspace.constant.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MusicAddRequest {
	@JsonIgnore
	private Integer id;

	@NotNull(message = "歌曲名称不能为空")
	private String title;

	private String author = "佚名";

	// TODO：可以搞个默认歌曲封面
	@NotNull(message = "歌曲封面不能为空")
	private String pic;

	@NotNull(message = "歌曲音频文件地址不能为空")
	private String url;

	private String lrc = "暂无";

	private String ownerComment = "暂无";

	private Integer status = StatusEnum.PUBLIC.getStatus();
}
