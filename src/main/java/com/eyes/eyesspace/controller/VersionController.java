package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesspace.exception.BizException;
import com.eyes.eyesspace.result.PageBind;
import com.eyes.eyesspace.result.Result;
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

	@Permission
	@GetMapping("/getVersionInfo")
	public Result<VersionInfoVO> getVersionInfo() throws BizException {
		return Result.success(versionService.getVersionInfo());
	}

	@Permission
	@GetMapping("/getVersionList")
	public Result<PageBind<VersionListDTO>> getVersionList(Integer page) throws BizException {
		return Result.success(versionService.getVersionList(page));
	}
}
