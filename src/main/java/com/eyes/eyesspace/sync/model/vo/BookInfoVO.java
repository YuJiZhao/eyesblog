package com.eyes.eyesspace.sync.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author artonyu
 * date 2024-08-01 10:06
 */

@Data
public class BookInfoVO {
	private String title;

	private String type;

	private String period;

	private String introduce;

	private String word;

	private Integer view;

	private Integer comment;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime createTime;
}
