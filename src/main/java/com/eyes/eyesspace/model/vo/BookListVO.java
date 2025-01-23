package com.eyes.eyesspace.model.vo;

import com.eyes.eyesspace.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author eyesYeager
 * data 2024/12/14 15:52
 */

@Data
public class BookListVO {
	private Integer id;

	private String title;

	private String author;

	private String type;

	private String period;

	private String introduce;

	private Integer view;

	@JsonFormat(pattern = DateUtils.DATE_FORMAT)
	private String createTime;
}
