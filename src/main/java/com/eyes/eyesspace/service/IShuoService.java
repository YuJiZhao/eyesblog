package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.exception.BizException;
import com.eyes.eyesspace.result.PageBind;
import com.eyes.eyesspace.model.entity.Shuoshuo;
import com.eyes.eyesspace.model.request.ShuoAddRequest;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.model.vo.ShuoListInfoVO;
import com.eyes.eyesspace.model.vo.ShuoListVO;

import org.springframework.web.multipart.MultipartFile;

public interface IShuoService extends IService<Shuoshuo> {
	void addShuo(ShuoAddRequest shuoAddRequest) throws BizException;

	FileUploadVO uploadShuoPic(MultipartFile multipartFile) throws BizException;

	ShuoListInfoVO getShuoListInfo() throws BizException;

	PageBind<ShuoListVO> getShuoList(Integer page) throws BizException;
}
