package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.exception.BizException;
import com.eyes.eyesspace.model.entity.Video;
import com.eyes.eyesspace.model.vo.VideoInfoVO;

public interface IVideoService extends IService<Video> {
	VideoInfoVO getVideoInfo() throws BizException;
}
