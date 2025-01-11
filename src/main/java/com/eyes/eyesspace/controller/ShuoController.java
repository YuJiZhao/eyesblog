package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesspace.exception.CustomException;
import com.eyes.eyesspace.result.PageBind;
import com.eyes.eyesspace.result.Result;
import com.eyes.eyesspace.model.request.ShuoAddRequest;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.model.vo.ShuoListInfoVO;
import com.eyes.eyesspace.model.vo.ShuoListVO;
import com.eyes.eyesspace.service.IShuoService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/shuo")
public class ShuoController {

	private final IShuoService shuoService;

	public ShuoController(IShuoService shuoService) {
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
	public Result<PageBind<ShuoListVO>> getShuoList(Integer page) throws CustomException {
		return Result.success(shuoService.getShuoList(page));
	}
}