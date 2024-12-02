package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesspace.persistent.dto.BlogCategoryDTO;
import com.eyes.eyesspace.persistent.dto.BlogInfoDTO;
import com.eyes.eyesspace.persistent.dto.BlogLabelDTO;
import com.eyes.eyesspace.persistent.dto.BlogListDTO;
import com.eyes.eyesspace.sync.common.exception.CustomException;
import com.eyes.eyesspace.sync.common.result.PageBind;
import com.eyes.eyesspace.sync.common.result.Result;
import com.eyes.eyesspace.sync.model.request.BlogAddRequest;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.BlogAddVO;
import com.eyes.eyesspace.sync.model.vo.BlogListInfoVO;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.service.BlogService;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author eyesYeager
 */
@RestController
@RefreshScope
@RequestMapping("/blog")
@Validated
public class BlogController {
	@Value("${business.page-size.blog:10}")
	private Integer blogPageSize;

	@Value("${business.comment.size:6}")
	private Integer commentSize;

	@Resource
	private BlogService blogService;

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/addBlog")
	public Result<BlogAddVO> addBlog(@Validated @RequestBody BlogAddRequest blogAddRequest) throws CustomException {
		return Result.success(blogService.addBlog(blogAddRequest));
	}

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/addBlogPic")
	public Result<FileUploadVO> addBlogPic(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
		return Result.success(blogService.addBlogPic(multipartFile));
	}

	@Limiter
	@Permission
	@GetMapping("/getBlogListInfo")
	public Result<BlogListInfoVO> getBlogListInfo() throws CustomException {
		return Result.success(blogService.getBlogListInfo());
	}

	@Limiter
	@Permission
	@GetMapping("/getBlogList")
	public Result<PageBind<BlogListDTO>> getBlogList(
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String label
	) throws CustomException {
		page = (Objects.isNull(page) || page < 1) ? 1 : page;
		// TODO：太难看了，记得改
		if (category.equals("undefined")) category = null;
		if (label.equals("undefined")) label = null;
		return Result.success(blogService.getBlogList(page, blogPageSize, category, label));
	}

	@Limiter
	@Permission
	@GetMapping("/getBlogInfo/{id}")
	public Result<BlogInfoDTO> getBlogInfo(
			@NotNull(message = "博客id不能为空")
			@PathVariable Integer id) throws CustomException {
		return Result.success(blogService.getBlogInfo(id));
	}

	@Limiter
	@Permission
	@GetMapping("/getBlogCategory")
	public Result<List<BlogCategoryDTO>> getBlogCategory() throws CustomException {
		return Result.success(blogService.getBlogCategory());
	}

	@Limiter
	@Permission
	@GetMapping("/getBlogLabel")
	public Result<List<BlogLabelDTO>> getBlogLabel() throws CustomException {
		return Result.success(blogService.getBlogLabel());
	}

	@Limiter
	@Permission(PermissionEnum.USER)
	@PostMapping("/doBlogComment")
	public Result<Void> doBlogComment(@Validated @RequestBody CommentAddRequest commentAddRequest) throws CustomException {
		blogService.doBlogComment(commentAddRequest);
		return Result.success();
	}

	@Limiter
	@Permission
	@GetMapping("/getBlogCommentList")
	public Result<List<CommentListVO>> getBlogCommentList(
			Integer id,
			@RequestParam(required = false) Integer page) throws CustomException {
		if (Objects.isNull(page) || page < 1) page = 1;
		return Result.success(blogService.getBlogCommentList(id, page, commentSize));
	}

	@Limiter
	@Permission(PermissionEnum.USER)
	@DeleteMapping("/delBlogComment/{id}")
	public Result<Void> delBlogComment(@PathVariable Integer id) throws CustomException {
		blogService.delBlogComment(id);
		return Result.success();
	}
}