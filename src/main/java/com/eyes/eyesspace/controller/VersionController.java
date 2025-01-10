package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.common.result.PageBind;
import com.eyes.eyesspace.common.result.Result;
import com.eyes.eyesspace.model.dto.VersionListDTO;
import com.eyes.eyesspace.model.vo.VersionInfoVO;
import com.eyes.eyesspace.service.IVersionService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author eyesYeager
 * date 2023/5/20 16:28
 */

@Validated
@RestController
@RequestMapping("/version")
public class VersionController {
	@Resource
	private IVersionService versionService;

	@Limiter
	@Permission
	@GetMapping("/getVersionInfo")
	public Result<VersionInfoVO> getVersionInfo() throws CustomException {
		return Result.success(versionService.getVersionInfo());
	}

	@Limiter
	@Permission
	@GetMapping("/getVersionList")
	public Result<PageBind<VersionListDTO>> getVersionList(Integer page) throws CustomException {
		return Result.success(versionService.getVersionList(page));
	}
}
