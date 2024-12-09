package com.eyes.eyesspace.model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserVideoKeyBean {
	private List<Integer> videos;

	private String time;

	private Integer num;
}
