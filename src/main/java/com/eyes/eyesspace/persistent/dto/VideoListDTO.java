package com.eyes.eyesspace.persistent.dto;

import lombok.Data;

@Data
public class VideoListDTO {
	private Integer id;

	private String title;

	private String pictureUrl;

	private Integer views;

	private Integer likes;

	private Integer comments;

	private Integer status;

	private String createTime;
}
