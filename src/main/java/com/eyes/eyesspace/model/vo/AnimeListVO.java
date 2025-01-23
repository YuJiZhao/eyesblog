package com.eyes.eyesspace.model.vo;

import com.eyes.eyesspace.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/5/22 17:55
 */

@Data
public class AnimeListVO {
	private Integer id;

	private String title;

	private String type;

	private String period;

	private String introduce;

	private String cover;

	private Integer view;

	@JsonFormat(pattern = DateUtils.DATE_FORMAT)
	private LocalDateTime createTime;
}
