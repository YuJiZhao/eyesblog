package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesspace.sync.common.exception.CustomException;
import com.eyes.eyesspace.sync.common.result.PageBind;
import com.eyes.eyesspace.sync.common.result.Result;
import com.eyes.eyesspace.sync.model.vo.HomeListVO;
import com.eyes.eyesspace.sync.model.vo.SiteDataVO;
import com.eyes.eyesspace.sync.service.HomeService;

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
 */

@RefreshScope
@RestController
@RequestMapping("/home")
@Validated
public class HomeController {
	@Value("${business.page-size.home:6}")
	private Integer homePageSize;

	@Resource
	private HomeService homeService;

	@Limiter
	@Permission
	@GetMapping("/getHomeList")
	public Result<PageBind<HomeListVO>> getHomeList(@RequestParam(required = false) Integer page) throws CustomException {
		page = (Objects.isNull(page) || page < 1) ? 1 : page;
		return Result.success(homeService.getHomeList(page, homePageSize));
	}

	@Limiter
	@Permission
	@GetMapping("/getSiteData")
	public Result<SiteDataVO> getSiteData() {
		return Result.success(homeService.getSiteData());
	}
}
