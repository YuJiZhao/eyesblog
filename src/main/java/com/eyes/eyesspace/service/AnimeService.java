package com.eyes.eyesspace.service;

import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.model.dto.AnimeListDTO;
import com.eyes.eyesspace.model.vo.AnimeInfoVO;
import com.eyes.eyesspace.model.vo.AnimeListInfoVO;
import com.eyes.eyesspace.model.vo.AnimeListVO;
import com.eyes.eyesspace.model.vo.AnimeNoticeVO;
import com.eyes.eyesspace.model.vo.FileUploadVO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author eyesYeager
 * date 2023/5/21 19:47
 */

public interface AnimeService {

	FileUploadVO uploadAnimePic(MultipartFile multipartFile) throws CustomException;

	AnimeNoticeVO getAnimeNotice();

	AnimeListInfoVO getAnimeListInfo();

	AnimeListVO getAnimeList(Integer page);

	AnimeInfoVO getAnimeInfo(Integer id) throws CustomException;

	List<AnimeListDTO> getAnimeListByIds(List<Integer> ids);
}
