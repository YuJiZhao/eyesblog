package com.eyes.eyesspace.service;

import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.common.result.PageBind;
import com.eyes.eyesspace.model.vo.HomeListVO;

public interface HomeService {
	PageBind<HomeListVO> getHomeList(Integer page) throws CustomException;
}
