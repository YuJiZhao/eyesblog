package com.eyes.eyesspace.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.eyes.eyesspace.mapper.ContextMapper;
import com.eyes.eyesspace.model.po.ContextPO;
import com.eyes.eyesspace.model.dto.FootprintDTO;
import com.eyes.eyesspace.model.vo.ContextAboutContentVO;
import com.eyes.eyesspace.model.vo.ContextVO;
import com.eyes.eyesspace.service.ContextService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ContextServiceImpl implements ContextService {
	private static final List<Integer> SITE_CONTEXT_IDS = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

	private static final List<Integer> ABOUT_ID = Collections.singletonList(8);

	@Resource
	private ContextMapper contextMapper;

	@Override
	public ContextVO getContext() {
		List<ContextPO> contextPart = contextMapper.getContext(SITE_CONTEXT_IDS);
		HashMap<String, Object> contextMap = new HashMap<>();
		for (ContextPO item : contextPart) {
			if (item.getName().equals("footprint")) {
				contextMap.put(item.getName(), JSONArray.parseArray(item.getValue(), FootprintDTO.class));
				continue;
			}
			contextMap.put(item.getName(), item.getValue());
		}
		return JSON.parseObject(JSON.toJSONString(contextMap), ContextVO.class);
	}

	@Override
	public ContextAboutContentVO getAboutContent() {
		List<ContextPO> context = contextMapper.getContext(ABOUT_ID);
		return new ContextAboutContentVO(context.get(0).getValue());
	}
}
