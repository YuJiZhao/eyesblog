package com.eyes.eyesspace.sync.model.vo;

import com.eyes.eyesspace.sync.model.dto.CommentChildDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class CommentListVO {
	private Integer id;

	@JsonIgnore
	private Long uid;

	private String avatar;

	private String name;

	private String comment;

	private String createTime;

	private Boolean owner;

	private Integer comments;

	private List<CommentChildDTO> children;
}