package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.common.result.PageBind;
import com.eyes.eyesspace.model.entity.Home;
import com.eyes.eyesspace.model.vo.HomeListVO;

public interface IHomeService extends IService<Home> {
	PageBind<HomeListVO> getHomeList(Integer page) throws CustomException;
}
