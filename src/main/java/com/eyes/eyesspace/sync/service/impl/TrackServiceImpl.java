package com.eyes.eyesspace.sync.service.impl;

import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.persistent.mapper.TrackMapper;
import com.eyes.eyesspace.sync.model.request.TrackPointAddRequest;
import com.eyes.eyesspace.sync.service.TrackService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.eyes.eyesspace.utils.BrowserUtils;
import com.eyes.eyesspace.utils.IpUtils;
import com.eyes.eyesspace.utils.OSUtils;
import com.eyes.eyesspace.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author eyesYeager
 * date 2023/2/9 15:53
 */
@Slf4j
@Service
public class TrackServiceImpl implements TrackService {

	@Resource
	private TrackMapper trackMapper;

	@Override
	public void addTrackPoint(TrackPointAddRequest trackPointAddRequest) {
		HttpServletRequest request = WebUtils.getRequest();
		if (!trackMapper.addTrackPoint(
				UserInfoHolder.getUid(),
				IpUtils.getIpAddr(request),
				OSUtils.osName(request),
				BrowserUtils.browserName(request),
				trackPointAddRequest)) {
			log.error("joke visit data addition error, trackPointAddRequest: {}", trackPointAddRequest);
		}
	}
}
