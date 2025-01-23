package com.eyes.eyesspace.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/5/21 13:53
 */

@Data
public class VersionListDTO {
	@JsonIgnore
	private Integer id;

	private String version;

	private Integer type;

	private String description;

	private String createTime;

	private List<String> picList;
}
