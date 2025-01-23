package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesspace.exception.BizException;
import com.eyes.eyesspace.result.PageBind;
import com.eyes.eyesspace.result.Result;
import com.eyes.eyesspace.model.vo.BookInfoVO;
import com.eyes.eyesspace.model.vo.BookListInfoVO;
import com.eyes.eyesspace.model.vo.BookListVO;
import com.eyes.eyesspace.service.IBookService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author eyesYeager
 * data 2024/12/9 20:52
 */

@Validated
@RestController
@RequestMapping("/book")
public class BookController {

	@Resource
	private IBookService bookService;

	@Permission
	@GetMapping("/getBookListInfo")
	public Result<BookListInfoVO> getBookListInfo() {
		return Result.success(bookService.getBookListInfo());
	}

	@Permission
	@GetMapping("/getBookList")
	public Result<PageBind<BookListVO>> getBookList(Integer page) {
		return Result.success(bookService.getBookList(page));
	}

	@Permission
	@GetMapping("/getBookInfo/{id}")
	public Result<BookInfoVO> getBookInfo(@PathVariable Integer id) throws BizException {
		return Result.success(bookService.getBookInfo(id));
	}
}
