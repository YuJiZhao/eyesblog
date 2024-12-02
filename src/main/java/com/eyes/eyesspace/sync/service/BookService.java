package com.eyes.eyesspace.sync.service;

import com.eyes.eyesspace.sync.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.dto.BookListDTO;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.*;

import java.util.List;

/**
 * @author artonyu
 * date 2024-08-01 09:38
 */

public interface BookService {
	BookNoticeVO getBookNotice();

	BookListInfoVO getBookListInfo();

	BookListVO getBookList(Integer page, Integer pageSize);

	BookInfoVO getBookInfo(Integer id) throws CustomException;

	List<BookListDTO> getBookListByIds(List<Integer> ids);

	void doBookComment(CommentAddRequest commentAddRequest) throws CustomException;

	List<CommentListVO> getBookCommentList(Integer id, Integer page, Integer pageSize) throws CustomException;

	void delBookComment(Integer id) throws CustomException;
}
