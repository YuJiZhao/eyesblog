package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.common.result.PageBind;
import com.eyes.eyesspace.model.dto.ShuoListDTO;
import com.eyes.eyesspace.model.entity.Shuoshuo;
import com.eyes.eyesspace.model.request.ShuoAddRequest;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.model.vo.ShuoListInfoVO;
import com.eyes.eyesspace.model.vo.ShuoListVO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IShuoService extends IService<Shuoshuo> {
	void addShuo(ShuoAddRequest shuoAddRequest) throws CustomException;

	FileUploadVO uploadShuoPic(MultipartFile multipartFile) throws CustomException;

	ShuoListInfoVO getShuoListInfo() throws CustomException;

	PageBind<ShuoListVO> getShuoList(Integer page) throws CustomException;

	List<ShuoListDTO> getShuoListByIds(List<Integer> ids);
}
