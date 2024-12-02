package com.eyes.eyesspace.sync.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/5/18 16:09
 */

@Data
@AllArgsConstructor
public class VideoAddCoverFailVO {
	private String title;

	private String bvid;

	private String error;
}
