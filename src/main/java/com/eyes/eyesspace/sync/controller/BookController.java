package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesspace.sync.common.exception.CustomException;
import com.eyes.eyesspace.sync.common.result.Result;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.*;
import com.eyes.eyesspace.sync.service.BookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author artonyu
 * date 2024-08-01 09:32
 */

@RefreshScope
@Validated
@RestController
@RequestMapping("/book")
public class BookController {
	@Value("${business.page-size.book:6}")
	private Integer bookPageSize;

	@Value("${business.comment.size:6}")
	private Integer commentSize;

	@Resource
	private BookService bookService;

	@Permission
	@Limiter
	@GetMapping("/getBookNotice")
	public Result<BookNoticeVO> getBookNotice() {
		return Result.success(bookService.getBookNotice());
	}

	@Permission
	@Limiter
	@GetMapping("/getBookListInfo")
	public Result<BookListInfoVO> getBookListInfo() {
		return Result.success(bookService.getBookListInfo());
	}

	@Permission
	@Limiter
	@GetMapping("/getBookList")
	public Result<BookListVO> getBookList(@RequestParam(required = false) Integer page) {
		page = (Objects.isNull(page) || page < 1) ? 1 : page;
		return Result.success(bookService.getBookList(page, bookPageSize));
	}

	@Permission
	@Limiter
	@GetMapping("/getBookInfo/{id}")
	public Result<BookInfoVO> getBookInfo(@PathVariable Integer id) throws CustomException {
		return Result.success(bookService.getBookInfo(id));
	}

	@Limiter
	@Permission(PermissionEnum.USER)
	@PostMapping("/doBookComment")
	public Result<Void> doBookComment(@Validated @RequestBody CommentAddRequest commentAddRequest) throws CustomException {
		bookService.doBookComment(commentAddRequest);
		return Result.success();
	}

	@Limiter
	@Permission
	@GetMapping("/getBookCommentList")
	public Result<List<CommentListVO>> getBookCommentList(
			Integer id,
			@RequestParam(required = false) Integer page) throws CustomException {
		if (Objects.isNull(page) || page < 1) page = 1;
		return Result.success(bookService.getBookCommentList(id, page, commentSize));
	}

	@Limiter
	@Permission(PermissionEnum.USER)
	@DeleteMapping("/delBookComment/{id}")
	public Result<Void> delBookComment(@PathVariable Integer id) throws CustomException {
		bookService.delBookComment(id);
		return Result.success();
	}
}
