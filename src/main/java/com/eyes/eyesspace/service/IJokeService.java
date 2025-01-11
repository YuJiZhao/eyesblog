package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.exception.CustomException;
import com.eyes.eyesspace.result.PageBind;
import com.eyes.eyesspace.model.dto.JokeListDTO;
import com.eyes.eyesspace.model.entity.Joke;
import com.eyes.eyesspace.model.request.JokeAddRequest;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.model.vo.JokeAddVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author eyesYeager
 * date 2023/9/25 8:50
 */
public interface IJokeService extends IService<Joke> {
	JokeAddVO addJoke(JokeAddRequest jokeAddRequest) throws CustomException;

	FileUploadVO uploadJokePic(MultipartFile multipartFile) throws CustomException;

	PageBind<JokeListDTO> getJokeList(Integer page);
}
