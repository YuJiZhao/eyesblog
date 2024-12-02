package com.eyes.eyesspace.sync.model.dto;

import java.util.List;

import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/9/25 11:21
 */

@Data
public class JokeListDTO {
	private Long id;

	private List<String> urlList;

	private String category;
}
