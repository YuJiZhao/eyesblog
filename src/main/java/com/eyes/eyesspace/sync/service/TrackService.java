package com.eyes.eyesspace.sync.service;

import com.eyes.eyesspace.sync.model.request.TrackPointAddRequest;

/**
 * @author eyesYeager
 * date 2023/2/9 15:53
 */

public interface TrackService {
	void addTrackPoint(TrackPointAddRequest trackPointAddRequest);
}
