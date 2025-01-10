package com.eyes.eyesspace.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.mapper.VideoMapper;
import com.eyes.eyesspace.model.entity.Video;
import com.eyes.eyesspace.model.po.VideoInfoPO;
import com.eyes.eyesspace.common.result.ResultCode;
import com.eyes.eyesspace.model.bean.UserVideoKeyBean;
import com.eyes.eyesspace.model.request.VideoAddBatchBiliRequest;
import com.eyes.eyesspace.model.request.VideoAddRequest;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.model.vo.VideoInfoVO;
import com.eyes.eyesspace.model.vo.VideoAddCoverFailVO;
import com.eyes.eyesspace.model.vo.VideoAddVO;
import com.eyes.eyesspace.service.IVideoService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

	// bilibili视频页面的base url，拼接bv号后成为原视频链接
	private static final String BILI_VIDEO_BASE_URL = "https://www.bilibili.com/video/";

	@Value("${path.folder.video}")
	private String videoPath;

	@Value("${path.folder.video-cover}")
	private String videoCoverPath;

	@Value("${business.media.video-max-play}")
	private Integer videoMaxPlayNum;

	@Value("${business.media.video-memory-num}")
	private Integer videoMemoryNum;

	@Resource
	private VideoMapper videoMapper;

	@Resource
	private FileUtils fileUtils;

	@Resource
	private SecurityUtils securityUtils;

	@Override
	public VideoAddVO addVideo(VideoAddRequest videoAddRequest) throws CustomException {
		if (!videoMapper.addVideo(videoAddRequest)) {
			throw new CustomException("视频添加失败！");
		}
		return new VideoAddVO(videoAddRequest.getId());
	}

	@Override
	public List<VideoAddCoverFailVO> addBatchBiliVideo(List<VideoAddBatchBiliRequest> videoAddBatchBiliRequestList) {
		// 批量处理视频信息
		List<VideoAddCoverFailVO> failVideoList = new ArrayList<>();
		List<VideoAddRequest> videoAddRequestList = videoAddBatchBiliRequestList.stream().map(v -> {
			VideoAddRequest videoAddRequest = new VideoAddRequest();
			BeanUtils.copyProperties(v, videoAddRequest);
			videoAddRequest.setOriginalAuthor(v.getName());
			// 构建视频原链接
			videoAddRequest.setOriginalUrl(BILI_VIDEO_BASE_URL + v.getBvid());
			// 上传视频封面
			try {
				String url = fileUtils.putObjectByUrl(v.getCover(), videoCoverPath);
				videoAddRequest.setPictureUrl(url);
			} catch (Exception e) {
				log.error("fail to add bili video:", e);
				failVideoList.add(new VideoAddCoverFailVO(
						v.getTitle(),
						v.getBvid(),
						e.getMessage()
				));
			}
			return videoAddRequest;
		}).toList();

		// 批量插入视频信息
		videoAddRequestList.forEach(v -> {
			if (!videoMapper.addVideo(v)) {
				failVideoList.add(new VideoAddCoverFailVO(
						v.getTitle(),
						v.getOriginalUrl(),
						"视频添加失败"
				));
			}
		});

		return failVideoList;
	}

	@Override
	public FileUploadVO addVideoCover(MultipartFile multipartFile) throws CustomException {
		String url = fileUtils.putObject(multipartFile, videoCoverPath);
		return new FileUploadVO(url);
	}

	@Override
	public FileUploadVO addVideoFile(MultipartFile multipartFile) throws CustomException {
		String url = fileUtils.putObject(multipartFile, videoPath);
		return new FileUploadVO(url);
	}

	@Override
	public VideoInfoVO getVideoInfo() throws CustomException {
		String role = UserInfoHolder.getRole();
		Long uid = UserInfoHolder.getUid();

		// Key处理
		String userVideoKey = videoMapper.getUserVideoKey(uid);
		VideoInfoPO videoInfoPo;
		if (Objects.isNull(userVideoKey)) {
			videoInfoPo = createUserVideoKey(uid, role);
		} else {
			videoInfoPo = updateUserVideoKey(userVideoKey, uid, role);
		}

		// 构建Dto
		VideoInfoVO uservideoInfoVO = new VideoInfoVO();
		BeanUtils.copyProperties(videoInfoPo, uservideoInfoVO);
		try {
			uservideoInfoVO.setId(securityUtils.symmetricEncrypt(videoInfoPo.getId().toString()));
		} catch (Exception e) {
			throw new CustomException("生成数据失败");
		}

		// 更新播放量
		uservideoInfoVO.setViews(uservideoInfoVO.getViews() + 1);
		if (!videoMapper.updateViewsNum(videoInfoPo.getId())) {
			log.error("视频播放量更新失败,id:{}", videoInfoPo.getId());
		}

		return uservideoInfoVO;
	}

	/**
	 * 创建UserVideoKey
	 */
	private VideoInfoPO createUserVideoKey(Long uid, String role) {
		VideoInfoPO randomVideoInfo = videoMapper.getRandomVideo(role, null);

		// 获取id列表
		ArrayList<Integer> idList = new ArrayList<>();
		idList.add(randomVideoInfo.getId());

		// 获取当前时间
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// 封装UserVideoKey
		UserVideoKeyBean userVideoKeyBean = new UserVideoKeyBean(
				idList,
				formatter.format(date),
				1
		);
		// 插入UserVideoKey
		if (!videoMapper.insertUserVideoKey(uid, JSON.toJSONString(userVideoKeyBean))) {
			log.error("UserVideoKey插入错误");
		}
		return randomVideoInfo;
	}

	/**
	 * 更新UserVideoKey
	 */
	private VideoInfoPO updateUserVideoKey(String userVideoKey, Long uid, String role) throws CustomException {
		UserVideoKeyBean userVideoKeyBean = JSON.parseObject(userVideoKey, UserVideoKeyBean.class);
		// 获取当前时间
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String time = formatter.format(date);
		// 判断是否达到播放上限
		if (userVideoKeyBean.getTime().equals(time) && userVideoKeyBean.getNum() >= videoMaxPlayNum) {
			throw new CustomException(ResultCode.NO_TIMES);
		}
		// 随机获取视频
		VideoInfoPO currentVideo = videoMapper.getRandomVideo(role, userVideoKeyBean.getVideos());
		if (Objects.isNull(currentVideo)) {
			throw new CustomException("暂无有效视频");
		}
		userVideoKeyBean.getVideos().add(currentVideo.getId());
		// 如果记忆视频数超过最大限度，则释放list头部元素
		if (userVideoKeyBean.getVideos().size() > videoMemoryNum) {
			userVideoKeyBean.getVideos().subList(0, 5).clear();
		}
		// 更新time与num
		if (userVideoKeyBean.getTime().equals(time)) {
			userVideoKeyBean.setNum(userVideoKeyBean.getNum() + 1);
		} else {
			userVideoKeyBean.setTime(time);
			userVideoKeyBean.setNum(1);
		}
		// 更新数据库key
		if (!videoMapper.updateUserVideoKey(uid, JSON.toJSONString(userVideoKeyBean))) {
			log.error("UserVideoKey更新错误");
		}
		return currentVideo;
	}
}