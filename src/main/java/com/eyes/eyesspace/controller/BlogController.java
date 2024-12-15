package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;

import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.common.result.PageBind;
import com.eyes.eyesspace.common.result.Result;
import com.eyes.eyesspace.model.dto.BlogCategoryDTO;
import com.eyes.eyesspace.model.dto.BlogInfoDTO;
import com.eyes.eyesspace.model.dto.BlogLabelDTO;
import com.eyes.eyesspace.model.dto.BlogListDTO;
import com.eyes.eyesspace.model.request.BlogAddRequest;
import com.eyes.eyesspace.model.vo.BlogAddVO;
import com.eyes.eyesspace.model.vo.BlogListInfoVO;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.service.BlogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author eyesYeager
 */

@Validated
@RestController
@RequestMapping("/blog")
public class BlogController {
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
			Integer page,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String label
	) throws CustomException {
		if (category.equals("undefined")) category = null;
		if (label.equals("undefined")) label = null;
		return Result.success(blogService.getBlogList(page, category, label));
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
}