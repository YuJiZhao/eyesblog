package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;

import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import com.eyes.eyesspace.exception.BizException;
import com.eyes.eyesspace.result.PageBind;
import com.eyes.eyesspace.result.Result;
import com.eyes.eyesspace.model.dto.BlogCategoryDTO;
import com.eyes.eyesspace.model.dto.BlogInfoDTO;
import com.eyes.eyesspace.model.dto.BlogLabelDTO;
import com.eyes.eyesspace.model.dto.BlogListDTO;
import com.eyes.eyesspace.model.request.BlogAddRequest;
import com.eyes.eyesspace.model.vo.BlogAddVO;
import com.eyes.eyesspace.model.vo.BlogListInfoVO;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.service.IBlogService;
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
	private IBlogService blogService;

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/addBlog")
	public Result<BlogAddVO> addBlog(@Validated @RequestBody BlogAddRequest blogAddRequest) throws BizException {
		return Result.success(blogService.addBlog(blogAddRequest));
	}

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/addBlogPic")
	public Result<FileUploadVO> addBlogPic(@RequestPart("file") MultipartFile multipartFile) throws BizException {
		return Result.success(blogService.addBlogPic(multipartFile));
	}

	@Permission
	@GetMapping("/getBlogListInfo")
	public Result<BlogListInfoVO> getBlogListInfo() throws BizException {
		return Result.success(blogService.getBlogListInfo());
	}

	@Permission
	@GetMapping("/getBlogList")
	public Result<PageBind<BlogListDTO>> getBlogList(
			Integer page,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String label
	) throws BizException {
		if (category.equals("undefined")) category = null;
		if (label.equals("undefined")) label = null;
		return Result.success(blogService.getBlogList(page, category, label));
	}

	@Permission
	@GetMapping("/getBlogInfo/{id}")
	public Result<BlogInfoDTO> getBlogInfo(
			@NotNull(message = "博客id不能为空")
			@PathVariable Integer id) throws BizException {
		return Result.success(blogService.getBlogInfo(id));
	}

	@Permission
	@GetMapping("/getBlogCategory")
	public Result<List<BlogCategoryDTO>> getBlogCategory() throws BizException {
		return Result.success(blogService.getBlogCategory());
	}

	@Permission
	@GetMapping("/getBlogLabel")
	public Result<List<BlogLabelDTO>> getBlogLabel() throws BizException {
		return Result.success(blogService.getBlogLabel());
	}
}