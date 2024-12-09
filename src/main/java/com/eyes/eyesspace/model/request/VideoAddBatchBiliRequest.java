package com.eyes.eyesspace.model.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/5/18 15:45
 */
@Data
public class VideoAddBatchBiliRequest {
	@NotNull(message = "视频标题不能为空")
	private String title;

	@NotNull(message = "视频封面链接不能为空")
	private String cover;

	@NotNull(message = "视频原作者不能为空")
	private String name;

	@NotNull(message = "视频bv号不能为空")
	private String bvid;

	@NotNull(message = "视频文件链接不能为空")
	private String videoUrl;

	private String ownerComment;
}
