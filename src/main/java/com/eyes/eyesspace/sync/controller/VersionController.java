package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesspace.sync.common.exception.CustomException;
import com.eyes.eyesspace.sync.common.result.Result;
import com.eyes.eyesspace.sync.model.vo.VersionInfoVO;
import com.eyes.eyesspace.sync.model.vo.VersionListVO;
import com.eyes.eyesspace.sync.service.VersionService;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author eyesYeager
 * date 2023/5/20 16:28
 */

@RefreshScope
@Validated
@RestController
@RequestMapping("/version")
public class VersionController {

	@Value("${business.page-size.version:6}")
	private Integer versionPageSize;

	@Resource
	private VersionService versionService;

	@Limiter
	@Permission
	@GetMapping("/getVersionInfo")
	public Result<VersionInfoVO> getVersionInfo() throws CustomException {
		return Result.success(versionService.getVersionInfo());
	}

	@Limiter
	@Permission
	@GetMapping("/getVersionList")
	public Result<VersionListVO> getVersionList(@RequestParam(required = false) Integer page) throws CustomException {
		page = (Objects.isNull(page) || page < 1) ? 1 : page;
		return Result.success(versionService.getVersionList(page, versionPageSize));
	}
}
