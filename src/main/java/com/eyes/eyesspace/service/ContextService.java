package com.eyes.eyesspace.service;

import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.model.vo.ContextAboutContentVO;
import com.eyes.eyesspace.model.vo.ContextVO;

public interface ContextService {

	ContextVO getContext() throws CustomException;

	ContextAboutContentVO getAboutContent();
}
