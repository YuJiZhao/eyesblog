package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesspace.exception.BizException;
import com.eyes.eyesspace.result.Result;
import com.eyes.eyesspace.model.vo.VideoInfoVO;
import com.eyes.eyesspace.service.IVideoService;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/video")
public class VideoController {
	@Resource
	private IVideoService videoService;

	@Permission(PermissionEnum.USER)
	@GetMapping("/getVideoInfo")
	public Result<VideoInfoVO> getVideoInfo() throws BizException {
		return Result.success(videoService.getVideoInfo());
	}
}
