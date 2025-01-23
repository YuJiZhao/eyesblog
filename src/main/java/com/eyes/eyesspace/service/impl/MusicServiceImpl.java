package com.eyes.eyesspace.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.exception.BizException;
import com.eyes.eyesspace.mapper.MusicMapper;
import com.eyes.eyesspace.model.entity.Music;
import com.eyes.eyesspace.model.vo.MusicInfoVO;
import com.eyes.eyesspace.service.IMusicService;

import com.eyes.eyesspace.utils.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements IMusicService {

	private static final int MAX_LIST_NUM = 25;

	@Resource
	private MusicMapper musicMapper;

	@Override
	public List<MusicInfoVO> getMusicList() {
		String role = UserInfoHolder.getRole();
		String statusSqlCondition = AuthUtils.statusSqlCondition(role);
		List<Music> randomMusicList = musicMapper.getRandomMusicList(statusSqlCondition, MAX_LIST_NUM);
		if (CollectionUtils.isEmpty(randomMusicList)) {
			return new ArrayList<>();
		}
		return randomMusicList.stream().map(v -> {
			MusicInfoVO musicInfoVO = new MusicInfoVO();
			BeanUtils.copyProperties(v, musicInfoVO);
			return musicInfoVO;
		}).toList();
	}

	@Override
	public String getMusicLrc(Integer id) throws BizException {
		Music music = musicMapper.selectById(id);
		if (Objects.isNull(music)) {
			throw new BizException("有什么 bug 记得给我发邮件");
		}
		return music.getLrc();
	}
}