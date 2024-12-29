package com.eyes.eyesspace.model.vo;

import lombok.Data;

/**
 * @author eyesYeager
 * data 2024/12/28 17:44
 */
@Data
public class FootprintInfoVO {
	private Integer id;

	private String country;

	private String province;

	private String city;

	private Double latitude;

	private Double longitude;
}
