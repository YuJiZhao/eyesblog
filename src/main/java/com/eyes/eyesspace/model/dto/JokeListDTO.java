package com.eyes.eyesspace.model.dto;

import java.util.List;

import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/9/25 11:21
 */

@Data
public class JokeListDTO {
	private Integer id;

	private List<String> urlList;

	private String category;
}
