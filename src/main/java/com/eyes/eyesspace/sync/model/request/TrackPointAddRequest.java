package com.eyes.eyesspace.sync.model.request;

import lombok.Data;

/**
 * @author artonyu
 * date 2024-08-05 09:38
 */

@Data
public class TrackPointAddRequest {
	private String browserId;

	private String sessionId;

	private String title;

	private String content;

	private String path;
}
