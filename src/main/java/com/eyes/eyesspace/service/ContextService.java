package com.eyes.eyesspace.service;

import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.model.vo.ContextItemVO;
import com.eyes.eyesspace.model.vo.ContextVO;

public interface ContextService {

	ContextVO getContext() throws CustomException;

	ContextItemVO getContextItem(Integer id);
}
