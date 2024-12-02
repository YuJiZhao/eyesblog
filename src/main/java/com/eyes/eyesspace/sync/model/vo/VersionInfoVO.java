package com.eyes.eyesspace.sync.model.vo;

import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/5/20 16:40
 */

@Data
public class VersionInfoVO {
	private String siteVersion;

	private String frontendVersion;

	private String backendVersion;

	private Integer versionNum;
}
