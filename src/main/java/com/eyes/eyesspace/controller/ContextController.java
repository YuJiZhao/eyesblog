package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesspace.exception.CustomException;
import com.eyes.eyesspace.result.Result;
import com.eyes.eyesspace.model.vo.ContextItemVO;
import com.eyes.eyesspace.model.vo.ContextVO;
import com.eyes.eyesspace.service.IContextService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	private IContextService contextService;

	@Limiter
	@Permission
	@GetMapping("/getContext")
	public Result<ContextVO> getContext() throws CustomException {
		return Result.success(contextService.getContext());
	}
	@Limiter
	@Permission
	@GetMapping("/getContextItem/{id}")
	public Result<ContextItemVO> getContextItem(@PathVariable Integer id) {
		return Result.success(contextService.getContextItem(id));
	}
}