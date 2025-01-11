package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.exception.CustomException;
import com.eyes.eyesspace.model.entity.Video;
import com.eyes.eyesspace.model.request.VideoAddBatchBiliRequest;
import com.eyes.eyesspace.model.request.VideoAddRequest;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.model.vo.VideoInfoVO;
import com.eyes.eyesspace.model.vo.VideoAddCoverFailVO;
import com.eyes.eyesspace.model.vo.VideoAddVO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IVideoService extends IService<Video> {
	VideoAddVO addVideo(VideoAddRequest videoAddRequest) throws CustomException;

	List<VideoAddCoverFailVO> addBatchBiliVideo(List<VideoAddBatchBiliRequest> videoAddBatchBiliRequestList);

	FileUploadVO addVideoCover(MultipartFile multipartFile) throws CustomException;

	FileUploadVO addVideoFile(MultipartFile multipartFile) throws CustomException;

	VideoInfoVO getVideoInfo() throws CustomException;
}
