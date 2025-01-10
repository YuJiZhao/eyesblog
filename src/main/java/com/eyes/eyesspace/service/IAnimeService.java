package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.common.result.PageBind;
import com.eyes.eyesspace.model.entity.Anime;
import com.eyes.eyesspace.model.vo.AnimeListVO;
import com.eyes.eyesspace.model.vo.AnimeInfoVO;
import com.eyes.eyesspace.model.vo.AnimeListInfoVO;
import com.eyes.eyesspace.model.vo.FileUploadVO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author eyesYeager
 * date 2023/5/21 19:47
 */

public interface IAnimeService extends IService<Anime> {

	FileUploadVO uploadAnimePic(MultipartFile multipartFile) throws CustomException;

	AnimeListInfoVO getAnimeListInfo();

	PageBind<AnimeListVO> getAnimeList(Integer page);

	AnimeInfoVO getAnimeInfo(Integer id) throws CustomException;

	List<AnimeListVO> getAnimeListByIds(List<Integer> ids);
}
