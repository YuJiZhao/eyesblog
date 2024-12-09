package com.eyes.eyesspace.model.dto;

import com.eyes.eyesspace.model.bean.HomeListBean;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/4/28 15:13
 */

@Data
public class ShuoListDTO implements HomeListBean {
	private Integer id;

	private String content;

	private List<String> picList;

	private Integer status;

	private String createTime;
}
