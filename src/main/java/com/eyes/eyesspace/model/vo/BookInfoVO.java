package com.eyes.eyesspace.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author eyesYeager
 * data 2024/12/14 17:13
 */

@Data
public class BookInfoVO {
	private String title;

	private String author;

	private String type;

	private String period;

	private String introduce;

	private String word;

	private Integer view;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private String createTime;
}
