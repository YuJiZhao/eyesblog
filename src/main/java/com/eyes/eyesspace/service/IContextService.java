package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.exception.CustomException;
import com.eyes.eyesspace.model.entity.Context;
import com.eyes.eyesspace.model.vo.ContextItemVO;
import com.eyes.eyesspace.model.vo.ContextVO;

public interface IContextService extends IService<Context> {

	ContextVO getContext() throws CustomException;

	ContextItemVO getContextItem(Integer id);
}
