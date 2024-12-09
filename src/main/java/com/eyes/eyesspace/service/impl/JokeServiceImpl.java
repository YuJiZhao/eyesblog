package com.eyes.eyesspace.service.impl;

import com.alibaba.fastjson.JSON;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.constant.MediaConstant;
import com.eyes.eyesspace.mapper.ContextMapper;
import com.eyes.eyesspace.mapper.JokeMapper;
import com.eyes.eyesspace.model.dto.JokeAddDTO;
import com.eyes.eyesspace.model.po.ContextPO;
import com.eyes.eyesspace.model.po.JokeAddCategoryPO;
import com.eyes.eyesspace.model.po.JokeListPO;
import com.eyes.eyesspace.common.result.PageBind;
import com.eyes.eyesspace.model.dto.JokeListDTO;
import com.eyes.eyesspace.model.request.JokeAddRequest;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.model.vo.JokeAddVO;
import com.eyes.eyesspace.model.vo.JokeNoticeVO;
import com.eyes.eyesspace.service.JokeService;
import com.eyes.eyesspace.utils.AuthUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.eyes.eyesspace.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author eyesYeager
 * date 2023/9/25 8:50
 */
@Slf4j
@Service
@RefreshScope
public class JokeServiceImpl implements JokeService {
	private static final List<Integer> JOKE_NOTICE_ID = Collections.singletonList(11);

	private static final Integer JOKE_PAGE_SIZE = 20;

	@Value("${path.folder.joke}")
	private String jokePath;

	@Resource
	private ContextMapper contextMapper;

	@Resource
	private JokeMapper jokeMapper;

	@Resource
	private FileUtils fileUtils;

	@Override
	@Transactional
	public JokeAddVO addJoke(JokeAddRequest jokeAddRequest) throws CustomException {
		JokeAddDTO jokeAddDTO = new JokeAddDTO();
		BeanUtils.copyProperties(jokeAddRequest, jokeAddDTO);
		// 获得梗图分类
		Long categoryIndex = jokeMapper.getCategoryIdByName(jokeAddRequest.getCategory());
		if (Objects.nonNull(categoryIndex)) {
			jokeAddDTO.setCategoryId(categoryIndex);
		} else {
			JokeAddCategoryPO jokeAddCategoryPO = new JokeAddCategoryPO();
			jokeAddCategoryPO.setCategory(jokeAddRequest.getCategory());
			if (!jokeMapper.addJokeCategory(jokeAddCategoryPO)) {
				throw new CustomException("新增分类失败！");
			}
			jokeAddDTO.setCategoryId(jokeAddCategoryPO.getId());
		}

		jokeAddDTO.setUrlList(JSON.toJSONString(jokeAddRequest.getUrlList()));
		// 插入梗图
		if (!jokeMapper.addJoke(jokeAddDTO)) {
			throw new CustomException("新增梗图失败！");
		}
		return new JokeAddVO(jokeAddDTO.getId());
	}

	@Override
	public FileUploadVO uploadJokePic(MultipartFile multipartFile) throws CustomException {
		String originalFilename = multipartFile.getOriginalFilename();
		if (Objects.isNull(originalFilename)) {
			throw new CustomException("文件错误");
		}
		String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
		if (!MediaConstant.imgContain(fileType)) {
			throw new CustomException("图片格式不支持");
		}
		String url = fileUtils.putObject(multipartFile, jokePath);
		return new FileUploadVO(url);
	}

	@Override
	public JokeNoticeVO getJokeNotice() {
		List<ContextPO> context = contextMapper.getContext(JOKE_NOTICE_ID);
		return new JokeNoticeVO(context.get(0).getValue());
	}

	@Override
	public PageBind<JokeListDTO> getJokeList(Integer page) {
		String role = UserInfoHolder.getRole();
		String statusCondition = AuthUtils.statusSqlCondition(role);
		List<JokeListPO> jokeList = jokeMapper.getJokeList((page - 1) * JOKE_PAGE_SIZE, JOKE_PAGE_SIZE, statusCondition);
		List<JokeListDTO> jokeDTOList = jokeList.stream().map(v -> {
			JokeListDTO jokeListDTO = new JokeListDTO();
			BeanUtils.copyProperties(v, jokeListDTO);
			jokeListDTO.setUrlList(JSON.parseArray(v.getUrlList(), String.class));
			return jokeListDTO;
		}).collect(Collectors.toList());
		return new PageBind<>(page, jokeMapper.getJokeTotalNum(statusCondition), jokeDTOList);
	}
}
