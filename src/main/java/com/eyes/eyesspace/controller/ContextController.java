package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesspace.exception.BizException;
import com.eyes.eyesspace.model.request.BatchContextItemRequest;
import com.eyes.eyesspace.result.Result;
import com.eyes.eyesspace.model.vo.ContextItemVO;
import com.eyes.eyesspace.service.IContextService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author eyesYeager
 */

@Validated
@RestController
@RequestMapping("/context")
public class ContextController {
	private static final List<Integer> SITE_CONTEXT_IDS = Arrays.asList(4, 5, 6, 7, 15, 18);

	@Resource
	private IContextService contextService;

	@Permission
	@GetMapping("/getContext")
	public Result<Map<String, String>> getContext() throws BizException {
		return Result.success(contextService.getBatchContextItem(SITE_CONTEXT_IDS));
	}

	@Permission
	@GetMapping("/getContextItem/{id}")
	public Result<ContextItemVO> getContextItem(@PathVariable Integer id) {
		return Result.success(contextService.getContextItem(id));
	}

	@Permission
	@PostMapping("/getBatchContextItem")
	public Result<Map<String, String>> getBatchContextItem(@Validated @RequestBody BatchContextItemRequest request) throws BizException {
		return Result.success(contextService.getBatchContextItem(request.getIdList()));
	}
}