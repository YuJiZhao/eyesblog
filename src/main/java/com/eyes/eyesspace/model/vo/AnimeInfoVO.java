package com.eyes.eyesspace.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/5/22 20:02
 */

@Data
public class AnimeInfoVO {
	private String title;

	private String type;

	private String period;

	private String introduce;

	private String word;

	private String cover;

	private Integer view;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime createTime;
}
