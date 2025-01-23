package com.eyes.eyesspace.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyes.eyesspace.mapper.ContextMapper;
import com.eyes.eyesspace.model.entity.Context;
import com.eyes.eyesspace.model.vo.ContextItemVO;
import com.eyes.eyesspace.service.IContextService;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ContextServiceImpl extends ServiceImpl<ContextMapper, Context> implements IContextService {
	@Resource
	private ContextMapper contextMapper;

	@Override
	public ContextItemVO getContextItem(Integer id) {
		Context context = contextMapper.selectById(id);
		return new ContextItemVO(context.getValue());
	}

	@Override
	public Map<String, String> getBatchContextItem(List<Integer> idList) {
		return contextMapper.selectByIds(idList).stream().collect(Collectors.toMap(Context::getName, Context::getValue));
	}
}
