package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.common.result.PageBind;
import com.eyes.eyesspace.common.result.Result;
import com.eyes.eyesspace.model.vo.HomeListVO;
import com.eyes.eyesspace.service.IHomeService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author eyesYeager
 */

@RestController
@RequestMapping("/home")
@Validated
public class HomeController {
	@Resource
	private IHomeService homeService;

	@Limiter
	@Permission
	@GetMapping("/getHomeList")
	public Result<PageBind<HomeListVO>> getHomeList(Integer page) throws CustomException {
		return Result.success(homeService.getHomeList(page));
	}
}
