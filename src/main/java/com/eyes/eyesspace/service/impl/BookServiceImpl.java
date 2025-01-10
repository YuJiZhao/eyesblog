package com.eyes.eyesspace.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.common.result.PageBind;
import com.eyes.eyesspace.mapper.BookMapper;
import com.eyes.eyesspace.model.entity.Book;
import com.eyes.eyesspace.model.vo.*;
import com.eyes.eyesspace.service.IBookService;
import com.eyes.eyesspace.utils.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author eyesYeager
 * data 2024/12/9 20:52
 */

@Slf4j
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

	private static final Integer BOOK_PAGE_SIZE = 6;

	@Resource
	private BookMapper bookMapper;

	@Override
	public BookListInfoVO getBookListInfo() {
		String role = UserInfoHolder.getRole();
		String statusCondition = AuthUtils.statusSqlCondition(role);
		return bookMapper.getBookListInfo(statusCondition);
	}

	@Override
	public PageBind<BookListVO> getBookList(Integer page) {
		String role = UserInfoHolder.getRole();
		String statusCondition = AuthUtils.statusSqlCondition(role);
		List<BookListVO> bookDTOList = bookMapper.getBookList((page - 1) * BOOK_PAGE_SIZE, BOOK_PAGE_SIZE, statusCondition);
		return new PageBind<>(page, bookMapper.getBookNum(statusCondition), bookDTOList);
	}

	@Override
	public BookInfoVO getBookInfo(Integer id) throws CustomException {
		String role = UserInfoHolder.getRole();
		String statusCondition = AuthUtils.statusSqlCondition(role);
		BookInfoVO result = bookMapper.getBookInfo(id, statusCondition);
		if (Objects.isNull(result)) {
			throw new CustomException("该书不存在");
		}
		// 更新点击量
		if (!bookMapper.addView(id)) {
			log.error("书单点击量更新失败");
		}
		return result;
	}
}
