package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.exception.CustomException;
import com.eyes.eyesspace.result.PageBind;
import com.eyes.eyesspace.model.entity.Book;
import com.eyes.eyesspace.model.vo.BookInfoVO;
import com.eyes.eyesspace.model.vo.BookListInfoVO;
import com.eyes.eyesspace.model.vo.BookListVO;

/**
 * @author eyesYeager
 * data 2024/12/9 20:52
 */

public interface IBookService extends IService<Book> {
	BookListInfoVO getBookListInfo();

	PageBind<BookListVO> getBookList(Integer page);

	BookInfoVO getBookInfo(Integer id) throws CustomException;
}
