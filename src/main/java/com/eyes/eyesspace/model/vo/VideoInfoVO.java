package com.eyes.eyesspace.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class VideoInfoVO {
	private String id;

	private String title;

	private String originalAuthor;

	private String pictureUrl;

	private String originalUrl;

	private String ownerComment;

	private String videoUrl;

	private Integer views;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;
}
