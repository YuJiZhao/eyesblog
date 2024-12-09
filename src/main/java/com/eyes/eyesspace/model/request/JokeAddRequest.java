package com.eyes.eyesspace.model.request;

import java.util.List;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/9/25 9:23
 */

@Data
public class JokeAddRequest {
	private List<String> urlList;

	@NotNull(message = "类别不能为空")
	private String category;

	private Integer status = 0;
}
