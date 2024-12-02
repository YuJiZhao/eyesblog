package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesspace.sync.common.exception.CustomException;
import com.eyes.eyesspace.sync.common.result.Result;
import com.eyes.eyesspace.sync.model.vo.ContextAboutContentVO;
import com.eyes.eyesspace.sync.model.vo.ContextVO;
import com.eyes.eyesspace.sync.service.ContextService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author eyesYeager
 */

@Validated
@RestController
@RequestMapping("/context")
public class ContextController {

	@Resource
	private ContextService contextService;

	@Limiter
	@Permission
	@GetMapping("/getContext")
	public Result<ContextVO> getContext() throws CustomException {
		return Result.success(contextService.getContext());
	}

	@Limiter
	@Permission
	@GetMapping("/getAboutContext")
	public Result<ContextAboutContentVO> getAboutContent() {
		return Result.success(contextService.getAboutContent());
	}
}