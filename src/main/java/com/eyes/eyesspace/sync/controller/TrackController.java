package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesspace.sync.model.request.TrackPointAddRequest;
import com.eyes.eyesspace.sync.service.TrackService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eyesYeager
 * date 2023/2/9 15:49
 */

@RestController
@RequestMapping("/track")
@Validated
public class TrackController {

	private final TrackService trackService;

	public TrackController(TrackService trackService) {
		this.trackService = trackService;
	}

	@Permission
	@PostMapping("/addTrackPoint")
	public void addTrackPoint(@RequestBody TrackPointAddRequest request) {
		trackService.addTrackPoint(request);
	}
}
