package com.eyes.eyesspace.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.exception.CustomException;
import com.eyes.eyesspace.mapper.MusicMapper;
import com.eyes.eyesspace.model.entity.Music;
import com.eyes.eyesspace.model.po.MusicInfoPO;
import com.eyes.eyesspace.result.ResultCode;
import com.eyes.eyesspace.model.bean.UserMusicKeyBean;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.model.vo.MusicAddVO;
import com.eyes.eyesspace.model.vo.MusicInfoVO;
import com.eyes.eyesspace.model.request.MusicAddRequest;
import com.eyes.eyesspace.service.IMusicService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import com.eyes.eyesspace.utils.FileUtils;
import com.eyes.eyesspace.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RefreshScope
@Slf4j
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements IMusicService {

	@Value("${path.folder.music}")
	private String musicPath;

	@Value("${path.folder.music-cover}")
	private String musicCoverPath;

	@Value("${business.media.music-max-play:5}")
	private Integer musicMaxPlayNum;

	@Value("${business.media.music-memory-num:20}")
	private Integer musicMemoryNum;

	@Resource
	private MusicMapper musicMapper;

	@Resource
	private FileUtils fileUtils;

	@Resource
	private SecurityUtils securityUtils;

	@Override
	public MusicAddVO addMusic(MusicAddRequest musicAddRequest) throws CustomException {
		if (!musicMapper.addMusic(musicAddRequest)) {
			throw new CustomException("音频添加失败！");
		}
		return new MusicAddVO(musicAddRequest.getId());
	}

	@Override
	public FileUploadVO addMusicCover(MultipartFile multipartFile) throws CustomException {
		String url = fileUtils.putObject(multipartFile, musicCoverPath);
		return new FileUploadVO(url);
	}

	@Override
	public FileUploadVO addMusicFile(MultipartFile multipartFile) throws CustomException {
		String url = fileUtils.putObject(multipartFile, musicPath);
		return new FileUploadVO(url);
	}

	@Override
	public String getMusicLrc(String id) throws CustomException {
		try {
			Integer musicId = Integer.valueOf(securityUtils.symmetricDecrypt(id));
			return musicMapper.getMusicLrc(musicId);
		} catch (NumberFormatException e) {
			throw new CustomException("这就很神奇了，但是没有歌词给你");
		} catch (Exception e) {
			throw new CustomException("歌词不存在");
		}
	}

	@Override
	public MusicInfoVO getMusicInfo() throws CustomException {
		String role = UserInfoHolder.getRole();
		Long uid = UserInfoHolder.getUid();

		// Key处理
		String userMusicKey = musicMapper.getUserMusicKey(uid);
		MusicInfoPO musicInfoPo;
		if (Objects.isNull(userMusicKey)) {
			musicInfoPo = createUserMusicKey(uid, role);
		} else {
			musicInfoPo = updateUserMusicKey(userMusicKey, uid, role);
		}

		// 构建Dto
		MusicInfoVO musicInfoVO = new MusicInfoVO();
		BeanUtils.copyProperties(musicInfoPo, musicInfoVO);
		try {
			musicInfoVO.setId(securityUtils.symmetricEncrypt(musicInfoPo.getId().toString()));
		} catch (Exception e) {
			throw new CustomException("生成数据失败");
		}

		// 更新播放量
		musicInfoVO.setViews(musicInfoVO.getViews() + 1);
		if (!musicMapper.updateViewsNum(musicInfoPo.getId())) {
			log.error("歌曲播放量更新失败,id:{}", musicInfoPo.getId());
		}

		return musicInfoVO;
	}

	/**
	 * 创建UserMusicKey
	 */
	private MusicInfoPO createUserMusicKey(Long uid, String role) {
		MusicInfoPO randomMusicInfo = musicMapper.getRandomMusicList(role, null);

		// 获取id列表
		ArrayList<Integer> idList = new ArrayList<>();
		idList.add(randomMusicInfo.getId());

		// 获取当前时间
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// 封装UserMusicKey
		UserMusicKeyBean userMusicKeyBean = new UserMusicKeyBean(
				idList,
				formatter.format(date),
				1
		);
		// 插入UserMusicKey
		if (!musicMapper.insertUserMusicKey(uid, JSON.toJSONString(userMusicKeyBean))) {
			log.error("UserMusicKey插入错误");
		}
		return randomMusicInfo;
	}

	/**
	 * 更新UserMusicKey
	 */
	private MusicInfoPO updateUserMusicKey(String userMusicKey, Long uid, String role) throws CustomException {
		UserMusicKeyBean userMusicKeyBean = JSON.parseObject(userMusicKey, UserMusicKeyBean.class);
		// 获取当前时间
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String time = formatter.format(date);
		// 判断是否达到播放上限
		if (userMusicKeyBean.getTime().equals(time) && userMusicKeyBean.getNum() >= musicMaxPlayNum) {
			throw new CustomException(ResultCode.NO_TIMES);
		}
		// 随机获取音频
		MusicInfoPO currentMusic = musicMapper.getRandomMusicList(role, userMusicKeyBean.getMusicList());
		if (Objects.isNull(currentMusic)) {
			throw new CustomException("暂无有效音频");
		}
		userMusicKeyBean.getMusicList().add(currentMusic.getId());
		// 如果记忆音频数超过最大限度，则释放list头部元素
		if (userMusicKeyBean.getMusicList().size() > musicMemoryNum) {
			userMusicKeyBean.getMusicList().subList(0, 5).clear();
		}
		// 更新time与num
		if (userMusicKeyBean.getTime().equals(time)) {
			userMusicKeyBean.setNum(userMusicKeyBean.getNum() + 1);
		} else {
			userMusicKeyBean.setTime(time);
			userMusicKeyBean.setNum(1);
		}
		// 更新数据库key
		if (!musicMapper.updateUserMusicKey(uid, JSON.toJSONString(userMusicKeyBean))) {
			log.error("UserMusicKey更新错误");
		}
		return currentMusic;
	}
}