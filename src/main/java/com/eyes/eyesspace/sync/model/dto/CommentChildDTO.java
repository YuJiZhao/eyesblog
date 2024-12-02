package com.eyes.eyesspace.sync.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CommentChildDTO {
	private Integer id;

	@JsonIgnore
	private Long uid;

	private Integer replyId;

	private String replyName;

	private String avatar;

	private String name;

	private String comment;

	private String createTime;

	private Boolean owner;

	private Integer comments;
}
