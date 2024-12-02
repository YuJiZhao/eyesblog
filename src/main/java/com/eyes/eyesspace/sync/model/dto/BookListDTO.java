package com.eyes.eyesspace.sync.model.dto;

import com.eyes.eyesspace.sync.model.bean.HomeListBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author artonyu
 * date 2024-08-01 10:04
 */

@Data
public class BookListDTO implements HomeListBean {
	private Integer id;

	private String title;

	private String type;

	private String period;

	private String introduce;

	private String cover;

	private Integer view;

	private Integer comment;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime createTime;
}
