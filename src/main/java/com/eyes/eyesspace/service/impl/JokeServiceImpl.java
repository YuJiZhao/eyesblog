package com.eyes.eyesspace.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyes.eyesAuth.constant.AuthConfigConstant;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.constant.StatusEnum;
import com.eyes.eyesspace.mapper.JokeMapper;
import com.eyes.eyesspace.model.entity.Joke;
import com.eyes.eyesspace.result.PageBind;
import com.eyes.eyesspace.model.dto.JokeListDTO;
import com.eyes.eyesspace.service.IJokeService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @author eyesYeager
 * date 2023/9/25 8:50
 */
@Slf4j
@Service
@RefreshScope
public class JokeServiceImpl extends ServiceImpl<JokeMapper, Joke> implements IJokeService {

	private static final Integer JOKE_PAGE_SIZE = 20;

	@Override
	public PageBind<JokeListDTO> getJokeList(Integer pageIndex) {
		String role = UserInfoHolder.getRole();
		Page<Joke> jokePage = new Page<>(pageIndex, JOKE_PAGE_SIZE);
		Page<Joke> page = page(jokePage, Wrappers.<Joke>lambdaQuery()
				.ne(AuthConfigConstant.ROLE_ADMIN.equals(role), Joke::getStatus, StatusEnum.DELETE.getStatus())
				.eq(!AuthConfigConstant.ROLE_ADMIN.equals(role), Joke::getStatus, StatusEnum.PUBLIC.getStatus())
				.orderByDesc(Joke::getCreateTime));
		List<JokeListDTO> jokeDTOList = page.getRecords().stream().map(v -> {
			JokeListDTO jokeListDTO = new JokeListDTO();
			jokeListDTO.setId(v.getId());
			jokeListDTO.setCategory(v.getCategory());
			jokeListDTO.setUrlList(JSON.parseArray(v.getUrlList(), String.class));
			return jokeListDTO;
		}).collect(Collectors.toList());
		return new PageBind<>(pageIndex, page.getTotal(), jokeDTOList);
	}
}
