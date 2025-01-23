package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesspace.exception.BizException;
import com.eyes.eyesspace.result.Result;
import com.eyes.eyesspace.model.vo.MusicInfoVO;
import com.eyes.eyesspace.service.IMusicService;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/music")
public class MusicController {
	@Resource
	private IMusicService musicService;

	@Permission(PermissionEnum.USER)
	@GetMapping("/getMusicList")
	public Result<List<MusicInfoVO>> getMusicList() throws BizException {
		return Result.success(musicService.getMusicList());
	}

	@GetMapping("/getMusicLrc")
	public String getMusicLrc(@NotNull(message = "id不能为空") Integer id) throws BizException {
		// 该方法由APlayer调用，无法自定义请求头，因此不做权限需求
		return musicService.getMusicLrc(id);
	}
}
