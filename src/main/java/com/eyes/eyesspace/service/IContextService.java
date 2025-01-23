package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.model.entity.Context;
import com.eyes.eyesspace.model.vo.ContextItemVO;

import java.util.List;
import java.util.Map;

public interface IContextService extends IService<Context> {

	ContextItemVO getContextItem(Integer id);

	Map<String, String> getBatchContextItem(List<Integer> idList);
}
