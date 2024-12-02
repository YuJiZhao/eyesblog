package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesspace.sync.common.exception.CustomException;
import com.eyes.eyesspace.sync.common.result.Result;
import com.eyes.eyesspace.sync.model.request.ShuoAddRequest;
import com.eyes.eyesspace.sync.model.request.ShuoCommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.vo.ShuoListInfoVO;
import com.eyes.eyesspace.sync.model.vo.ShuoListVO;
import com.eyes.eyesspace.sync.service.ShuoService;

import java.util.List;
import java.util.Objects;

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

@RefreshScope
@Validated
@RestController
@RequestMapping("/shuo")
public class ShuoController {
	@Value("${business.page-size.shuo:10}")
	private Integer shuoPageSize;

	@Value("${business.comment.size:6}")
	private Integer commentSize;

	private final ShuoService shuoService;

	public ShuoController(ShuoService shuoService) {
		this.shuoService = shuoService;
	}

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/addShuo")
	public Result<Void> addShuo(@Validated @RequestBody ShuoAddRequest shuoAddRequest) throws CustomException {
		shuoService.addShuo(shuoAddRequest);
		return Result.success();
	}

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/uploadShuoPic")
	public Result<FileUploadVO> uploadShuoPic(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
		return Result.success(shuoService.uploadShuoPic(multipartFile));
	}

	@Limiter
	@Permission
	@GetMapping("/getShuoListInfo")
	public Result<ShuoListInfoVO> getShuoListInfo() throws CustomException {
		return Result.success(shuoService.getShuoListInfo());
	}

	@Limiter
	@Permission
	@GetMapping("/getShuoList")
	public Result<List<ShuoListVO>> getShuoList(@RequestParam(required = false) Integer page) throws CustomException {
		page = (Objects.isNull(page) || page < 1) ? 1 : page;
		return Result.success(shuoService.getShuoList(page, shuoPageSize));
	}

	@Limiter
	@Permission
	@GetMapping("/getSingleShuo")
	public Result<ShuoListVO> getShuoInfoByString(String id) throws CustomException {
		return Result.success(shuoService.getShuoInfo(id));
	}

	@Limiter
	@Permission(PermissionEnum.USER)
	@PostMapping("/doShuoComment")
	public Result<Void> doShuoComment(@Validated @RequestBody ShuoCommentAddRequest shuoCommentAddRequest) throws CustomException {
		shuoService.doShuoComment(shuoCommentAddRequest);
		return Result.success();
	}

	@Limiter
	@Permission
	@GetMapping("/getShuoCommentList")
	public Result<List<CommentListVO>> getShuoCommentList(
			String id,
			@RequestParam(required = false) Integer page) throws CustomException {
		if (Objects.isNull(page) || page < 1) page = 1;
		return Result.success(shuoService.getShuoCommentList(id, page, commentSize));
	}

	@Limiter
	@Permission(PermissionEnum.USER)
	@DeleteMapping("/delShuoComment/{id}")
	public Result<Void> delShuoComment(@PathVariable Integer id) throws CustomException {
		shuoService.delShuoComment(id);
		return Result.success();
	}
}