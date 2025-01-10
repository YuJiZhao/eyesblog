package com.eyes.eyesspace.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.constant.HomeTypeEnum;
import com.eyes.eyesspace.mapper.HomeMapper;
import com.eyes.eyesspace.model.dto.BlogListDTO;
import com.eyes.eyesspace.model.entity.Home;
import com.eyes.eyesspace.model.po.HomeListPO;
import com.eyes.eyesspace.common.result.PageBind;
import com.eyes.eyesspace.model.vo.AnimeListVO;
import com.eyes.eyesspace.model.dto.ShuoListDTO;
import com.eyes.eyesspace.model.dto.VersionListDTO;
import com.eyes.eyesspace.model.vo.HomeListVO;
import com.eyes.eyesspace.service.*;
import com.eyes.eyesspace.utils.AuthUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HomeServiceImpl extends ServiceImpl<HomeMapper, Home> implements IHomeService {

	private final int HOME_PAGE_SIZE = 6;

	@Resource
	private HomeMapper homeMapper;

	@Resource
	private IBlogService IBlogService;

	@Resource
	private IShuoService shuoService;

	@Resource
	private IVersionService versionService;

	@Resource
	private IAnimeService animeService;

	@Override
	public PageBind<HomeListVO> getHomeList(Integer page) throws CustomException {
		String role = UserInfoHolder.getRole();
		String statusCondition = AuthUtils.statusSqlCondition(role);

		// 获取home列表
		List<HomeListPO> homeList = homeMapper.getHomeList((page - 1) * HOME_PAGE_SIZE, HOME_PAGE_SIZE, statusCondition);

		// 整合相同类型内容
		Map<Integer, List<Integer>> homeMap = new HashMap<>();
		for (HomeListPO item : homeList) {
			if (homeMap.containsKey(item.getType())) {
				homeMap.get(item.getType()).add(item.getCid());
			} else {
				List<Integer> list = new ArrayList<>();
				list.add(item.getCid());
				homeMap.put(item.getType(), list);
			}
		}

		// 执行查询
		Map<Integer, BlogListDTO> blogMap = new HashMap<>();
		Map<Integer, ShuoListDTO> shuoMap = new HashMap<>();
		Map<Integer, VersionListDTO> versionMap = new HashMap<>();
		Map<Integer, AnimeListVO> animeMap = new HashMap<>();
		for (Map.Entry<Integer, List<Integer>> entry : homeMap.entrySet()) {
			if (HomeTypeEnum.BLOG.getType().equals(entry.getKey())) {
				// 博客列表
				List<BlogListDTO> blogList = IBlogService.getBlogListByIds(entry.getValue());
				blogMap = blogList.stream().collect(Collectors.toMap(BlogListDTO::getId, o -> o, (front, behind) -> front));
			} else if (HomeTypeEnum.SHUOSHUO.getType().equals(entry.getKey())) {
				// 说说列表
				List<ShuoListDTO> shuoList = shuoService.getShuoListByIds(entry.getValue());
				shuoMap = shuoList.stream().collect(Collectors.toMap(ShuoListDTO::getId, o -> o, (front, behind) -> front));
			} else if (HomeTypeEnum.VERSION.getType().equals(entry.getKey())) {
				// 版本列表
				List<VersionListDTO> versionList = versionService.getVersionListByIds(entry.getValue());
				versionMap = versionList.stream().collect(Collectors.toMap(VersionListDTO::getId, o -> o, (front, behind) -> front));
			} else if (HomeTypeEnum.ANIME.getType().equals(entry.getKey())) {
				// 动漫列表
				List<AnimeListVO> animeList = animeService.getAnimeListByIds(entry.getValue());
				animeMap = animeList.stream().collect(Collectors.toMap(AnimeListVO::getId, o -> o, (front, behind) -> front));
			} else {
				log.error("wrong content type: " + entry.getKey());
			}
		}

		// 组装数据
		List<HomeListVO> homeListBeanList = new ArrayList<>();
		for (HomeListPO item : homeList) {
			HomeListVO homeItem = new HomeListVO();
			if (HomeTypeEnum.BLOG.getType().equals(item.getType())) {  // 博客列表
				homeItem.setType(HomeTypeEnum.BLOG.getType());
				homeItem.setHomeList(blogMap.get(item.getCid()));
				homeListBeanList.add(homeItem);
			} else if (HomeTypeEnum.SHUOSHUO.getType().equals(item.getType())) {  // 说说列表
				homeItem.setType(HomeTypeEnum.SHUOSHUO.getType());
				homeItem.setHomeList(shuoMap.get(item.getCid()));
				homeListBeanList.add(homeItem);
			} else if (HomeTypeEnum.VERSION.getType().equals(item.getType())) {
				homeItem.setType(HomeTypeEnum.VERSION.getType());
				homeItem.setHomeList(versionMap.get(item.getCid()));
				homeListBeanList.add(homeItem);
			} else if (HomeTypeEnum.ANIME.getType().equals(item.getType())) {
				homeItem.setType(HomeTypeEnum.ANIME.getType());
				homeItem.setHomeList(animeMap.get(item.getCid()));
				homeListBeanList.add(homeItem);
			} else {
				log.error("wrong content type: " + item.getType());
			}
		}

		PageBind<HomeListVO> result = new PageBind<>();
		result.setData(homeListBeanList);
		result.setPage(page);
		result.setTotal(homeMapper.getHomeListTotal(statusCondition));
		return result;
	}
}
