package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.exception.BizException;
import com.eyes.eyesspace.model.entity.Music;
import com.eyes.eyesspace.model.vo.MusicInfoVO;

import java.util.List;

public interface IMusicService extends IService<Music> {
	List<MusicInfoVO> getMusicList() throws BizException;

	String getMusicLrc(Integer id) throws BizException;
}
