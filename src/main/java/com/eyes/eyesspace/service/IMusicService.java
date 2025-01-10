package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.model.entity.Music;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.model.vo.MusicAddVO;
import com.eyes.eyesspace.model.vo.MusicInfoVO;
import com.eyes.eyesspace.model.request.MusicAddRequest;
import org.springframework.web.multipart.MultipartFile;

public interface IMusicService extends IService<Music> {
	MusicAddVO addMusic(MusicAddRequest musicAddRequest) throws CustomException;

	FileUploadVO addMusicCover(MultipartFile multipartFile) throws CustomException;

	FileUploadVO addMusicFile(MultipartFile multipartFile) throws CustomException;

	String getMusicLrc(String id) throws CustomException;

	MusicInfoVO getMusicInfo() throws CustomException;
}
