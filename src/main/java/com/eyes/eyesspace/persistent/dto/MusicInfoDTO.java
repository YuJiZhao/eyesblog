package com.eyes.eyesspace.persistent.dto;

import lombok.Data;

@Data
public class MusicInfoDTO {
	private String title;

	private String author;

	private String url;

	private String pic;

	private String ownerComment;

	private Integer views;

	private Integer likes;

	private Integer comments;

	private String createTime;
}
