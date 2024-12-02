package com.eyes.eyesspace.sync.model.vo;

import lombok.Data;

@Data
public class UserMusicInfoVO {
	private String id;

	private String title;

	private String author;

	private String url;

	private String pic;

	private Integer views;

	private Integer likes;

	private Integer comments;

	private Boolean isLike;
}
