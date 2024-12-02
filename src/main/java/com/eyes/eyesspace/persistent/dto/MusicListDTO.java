package com.eyes.eyesspace.persistent.dto;

import lombok.Data;

@Data
public class MusicListDTO {
	private Integer id;

	private String title;

	private String author;

	private String pic;

	private Integer views;

	private Integer likes;

	private Integer comments;

	private Integer status;

	private String createTime;
}
