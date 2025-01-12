package com.eyes.eyesspace.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页工具类
 *
 * @author eyes
 * date 2023/1/11 9:25
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBind<T> {
	private Integer page;

	private Long total;

	private List<T> data;
}
