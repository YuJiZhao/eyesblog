package com.eyes.eyesspace.service;

import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.common.result.PageBind;
import com.eyes.eyesspace.model.dto.JokeListDTO;
import com.eyes.eyesspace.model.request.JokeAddRequest;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.model.vo.JokeAddVO;
import com.eyes.eyesspace.model.vo.JokeNoticeVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author eyesYeager
 * date 2023/9/25 8:50
 */
public interface JokeService {
	JokeAddVO addJoke(JokeAddRequest jokeAddRequest) throws CustomException;

	FileUploadVO uploadJokePic(MultipartFile multipartFile) throws CustomException;

	JokeNoticeVO getJokeNotice();

	PageBind<JokeListDTO> getJokeList(Integer page);
}
