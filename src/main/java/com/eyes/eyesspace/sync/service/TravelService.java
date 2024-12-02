package com.eyes.eyesspace.sync.service;

import com.eyes.eyesspace.sync.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.vo.TravelNoticeVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author artonyu
 * date 2024-08-01 11:44
 */

public interface TravelService {
	FileUploadVO uploadTravelPic(MultipartFile multipartFile) throws CustomException;

	TravelNoticeVO getTravelNotice();

	void doTravelComment(CommentAddRequest commentAddRequest) throws CustomException;

	List<CommentListVO> getTravelCommentList(Integer id, Integer page, Integer pageSize) throws CustomException;

	void delTravelComment(Integer id) throws CustomException;
}
