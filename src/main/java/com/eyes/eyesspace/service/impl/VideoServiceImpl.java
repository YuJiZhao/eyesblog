package com.eyes.eyesspace.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.exception.BizException;
import com.eyes.eyesspace.mapper.VideoMapper;
import com.eyes.eyesspace.model.entity.Video;
import com.eyes.eyesspace.model.vo.VideoInfoVO;
import com.eyes.eyesspace.service.IVideoService;
import com.eyes.eyesspace.utils.AuthUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

	private static final int MAX_VIDEO_NUM = 500;

	private final Cache<Long, Integer> videoLimitCache = CacheBuilder.newBuilder()
			.initialCapacity(2)
			.maximumSize(64)
			.expireAfterWrite(24, TimeUnit.HOURS)
			.build();

	@Resource
	private VideoMapper videoMapper;

	@Override
	public VideoInfoVO getVideoInfo() throws BizException {
		// 限制校验
		Long uid = UserInfoHolder.getUid();
		try {
			Integer num = videoLimitCache.getIfPresent(uid);
			if (Objects.nonNull(num) && num >= MAX_VIDEO_NUM) {
				throw new BizException("超出观看限制！");
			}
			videoLimitCache.put(uid, Objects.isNull(num) ? 1 : num + 1);
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}
		// 获取视频信息
		String role = UserInfoHolder.getRole();
		String statusSqlCondition = AuthUtils.statusSqlCondition(role);
		Video video = videoMapper.getRandomVideo(statusSqlCondition);
		VideoInfoVO videoInfoVO = new VideoInfoVO();
		BeanUtils.copyProperties(video, videoInfoVO);
		return videoInfoVO;
	}
}