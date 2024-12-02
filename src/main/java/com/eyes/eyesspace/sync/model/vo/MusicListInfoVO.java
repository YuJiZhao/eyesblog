package com.eyes.eyesspace.sync.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MusicListInfoVO {
	private Integer totalNum;

	private Integer publicNum;

	private Integer privateNum;

	private Integer deleteNum;

	private Integer viewsNum;

	private Integer likesNum;

	private Integer commentsNum;
}
