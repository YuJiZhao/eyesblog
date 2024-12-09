package com.eyes.eyesspace.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class VideoInfoPO {
	private Integer id;

	private String title;

	private String originalAuthor;

	private String pictureUrl;

	private String originalUrl;

	private String ownerComment;

	private String videoUrl;

	private Integer views;

	private Date createTime;
}
