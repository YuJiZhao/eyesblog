package com.eyes.eyesspace.service.impl;

import com.eyes.eyesAuth.constant.AuthConfigConstant;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.common.result.PageBind;
import com.eyes.eyesspace.constant.MediaConstant;
import com.eyes.eyesspace.constant.StatusEnum;
import com.eyes.eyesspace.mapper.AnimeMapper;
import com.eyes.eyesspace.model.vo.AnimeListVO;
import com.eyes.eyesspace.model.vo.AnimeInfoVO;
import com.eyes.eyesspace.model.vo.AnimeListInfoVO;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.service.AnimeService;
import com.eyes.eyesspace.utils.AuthUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.eyes.eyesspace.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author eyesYeager
 * date 2023/5/21 19:47
 */
@Slf4j
@Service
@RefreshScope
public class AnimeServiceImpl implements AnimeService {

	private static final Integer ANIME_PAGE_SIZE = 6;

	@Value("${path.folder.anime}")
	private String animePath;

	@Resource
	private AnimeMapper animeMapper;

	@Resource
	private FileUtils fileUtils;

	@Override
	public FileUploadVO uploadAnimePic(MultipartFile multipartFile) throws CustomException {
		String originalFilename = multipartFile.getOriginalFilename();
		if (Objects.isNull(originalFilename)) {
			throw new CustomException("文件错误");
		}
		String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
		if (!MediaConstant.imgContain(fileType)) {
			throw new CustomException("图片格式不支持");
		}

		String url = fileUtils.putObject(multipartFile, animePath);
		return new FileUploadVO(url);
	}

	@Override
	public AnimeListInfoVO getAnimeListInfo() {
		String role = UserInfoHolder.getRole();
		String statusCondition = AuthUtils.statusSqlCondition(role);
		List<Integer> statusList = animeMapper.getAnimeStatusList();
		Map<Integer, Long> statusMap = statusList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		AnimeListInfoVO result = new AnimeListInfoVO(
				animeMapper.getAnimeViewNum(statusCondition)
		);
		// 游客与普通用户只返回三个字段
		if (!AuthConfigConstant.ROLE_ADMIN.equals(role)) {
			result.setTotalNum(statusMap.getOrDefault(StatusEnum.PUBLIC.getStatus(), 0L));
			return result;
		}
		// 对于管理员，还需要返回公开、私有、删除数据
		result.setTotalNum((long) statusList.size());
		result.setPublicNum(statusMap.getOrDefault(StatusEnum.PUBLIC.getStatus(), 0L));
		result.setPrivateNum(statusMap.getOrDefault(StatusEnum.PRIVATE.getStatus(), 0L));
		result.setDeleteNum(statusMap.getOrDefault(StatusEnum.DELETE.getStatus(), 0L));
		return result;
	}

	@Override
	public PageBind<AnimeListVO> getAnimeList(Integer page) {
		String role = UserInfoHolder.getRole();
		String statusCondition = AuthUtils.statusSqlCondition(role);
		List<AnimeListVO> animeDTOList = animeMapper.getAnimeList((page - 1) * ANIME_PAGE_SIZE, ANIME_PAGE_SIZE, statusCondition);
		return new PageBind<>(page, animeMapper.getAnimeNum(statusCondition), animeDTOList);
	}

	@Override
	public AnimeInfoVO getAnimeInfo(Integer id) throws CustomException {
		String role = UserInfoHolder.getRole();
		String statusCondition = AuthUtils.statusSqlCondition(role);
		AnimeInfoVO result = animeMapper.getAnimeInfo(id, statusCondition);
		if (Objects.isNull(result)) {
			throw new CustomException("该动漫不存在");
		}
		// 更新点击量
		if (!animeMapper.addView(id)) {
			log.error("动漫点击量更新失败");
		}
		return result;
	}

	@Override
	public List<AnimeListVO> getAnimeListByIds(List<Integer> ids) {
		return animeMapper.getAnimeListByIds(ids);
	}
}
