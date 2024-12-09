package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.common.result.Result;
import com.eyes.eyesspace.model.request.VideoAddBatchBiliRequest;
import com.eyes.eyesspace.model.request.VideoAddRequest;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.model.vo.VideoInfoVO;
import com.eyes.eyesspace.model.vo.VideoAddCoverFailVO;
import com.eyes.eyesspace.model.vo.VideoAddVO;
import com.eyes.eyesspace.service.VideoService;

import java.util.List;
import javax.annotation.Resource;

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
@RequestMapping("/video")
public class VideoController {
	@Resource
	private VideoService videoService;

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/addVideo")
	public Result<VideoAddVO> addVideo(@Validated @RequestBody VideoAddRequest videoAddRequest) throws CustomException {
		return Result.success(videoService.addVideo(videoAddRequest));
	}

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/addBatchBiliVideo")
	public Result<List<VideoAddCoverFailVO>> addBatchBiliVideo(@Validated @RequestBody List<VideoAddBatchBiliRequest> videoAddBatchBiliRequestList) {
		return Result.success(videoService.addBatchBiliVideo(videoAddBatchBiliRequestList));
	}

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/addVideoCover")
	public Result<FileUploadVO> addVideoCover(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
		return Result.success(videoService.addVideoCover(multipartFile));
	}

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/addVideoFile")
	public Result<FileUploadVO> addVideoFile(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
		return Result.success(videoService.addVideoFile(multipartFile));
	}

	@Limiter
	@Permission(PermissionEnum.USER)
	@GetMapping("/getVideoInfo")
	public Result<VideoInfoVO> getVideoInfo() throws CustomException {
		return Result.success(videoService.getVideoInfo());
	}
}
