package com.eyes.eyesspace.persistent.dto;

import lombok.Data;

@Data
public class VideoInfoDTO {
	private String title;

	private String originalAuthor;

	private String pictureUrl;

	private String originalUrl;

	private String ownerComment;

	private String videoUrl;

	private Integer views;

	private Integer likes;

	private Integer comments;

	private String createTime;
}
