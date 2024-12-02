package com.eyes.eyesspace.persistent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import com.eyes.eyesspace.sync.model.bean.HomeListBean;

import java.util.Date;

@Data
public class BlogListDTO implements HomeListBean {
	private Integer id;

	private String title;

	private String summary;

	private String category;

	private Integer views;

	private Integer words;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer status;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;
}
