package com.eyes.eyesspace.model.vo;

import com.eyes.eyesspace.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoInfoVO {
	private String title;

	private String author;

	private String videoUrl;

	private String coverUrl;

	private String originalUrl;

	private String comment;

	@JsonFormat(pattern = DateUtils.DATE_FORMAT)
	private LocalDateTime createTime;
}
