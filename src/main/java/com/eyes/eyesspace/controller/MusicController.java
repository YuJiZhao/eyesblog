package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.common.result.Result;
import com.eyes.eyesspace.model.request.MusicAddRequest;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.model.vo.MusicAddVO;
import com.eyes.eyesspace.model.vo.MusicInfoVO;
import com.eyes.eyesspace.service.MusicService;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/music")
public class MusicController {
	@Resource
	private MusicService musicService;

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/addMusic")
	public Result<MusicAddVO> addMusic(@Validated @RequestBody MusicAddRequest musicAddRequest) throws CustomException {
		return Result.success(musicService.addMusic(musicAddRequest));
	}

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/addMusicCover")
	public Result<FileUploadVO> addMusicCover(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
		return Result.success(musicService.addMusicCover(multipartFile));
	}

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/addMusicFile")
	public Result<FileUploadVO> addMusicFile(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
		return Result.success(musicService.addMusicFile(multipartFile));
	}

	@Limiter
	@Permission(PermissionEnum.USER)
	@GetMapping("/getMusicInfo")
	public Result<MusicInfoVO> getMusicInfo() throws CustomException {
		return Result.success(musicService.getMusicInfo());
	}

	@Limiter
	@GetMapping("/getMusicLrc")
	public String getMusicLrc(@NotNull(message = "id不能为空") String id) throws CustomException {
		// 该方法由APlayer调用，无法自定义请求头，因此不做权限需求
		return musicService.getMusicLrc(id);
	}
}
