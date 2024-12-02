package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesspace.persistent.dto.VideoInfoDTO;
import com.eyes.eyesspace.persistent.dto.VideoListDTO;
import com.eyes.eyesspace.sync.common.exception.CustomException;
import com.eyes.eyesspace.sync.common.result.Result;
import com.eyes.eyesspace.sync.model.request.VideoAddBatchBiliRequest;
import com.eyes.eyesspace.sync.model.request.VideoAddRequest;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.vo.UserVideoInfoVO;
import com.eyes.eyesspace.sync.model.vo.VideoAddCoverFailVO;
import com.eyes.eyesspace.sync.model.vo.VideoAddVO;
import com.eyes.eyesspace.sync.model.vo.VideoListInfoVO;
import com.eyes.eyesspace.sync.service.VideoService;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RefreshScope
@Validated
@RestController
@RequestMapping("/video")
public class VideoController {

	@Value("${business.page-size.video:10}")
	private Integer videoPageSize;

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

	@Permission(PermissionEnum.ADMIN)
	@GetMapping("/getVideoList")
	public Result<List<VideoListDTO>> getVideoList(@RequestParam(required = false) Integer page) {
		page = (Objects.isNull(page) || page < 1) ? 1 : page;
		return Result.success(videoService.getVideoList(page, videoPageSize));
	}

	@Permission(PermissionEnum.ADMIN)
	@GetMapping("/getVideoListInfo")
	public Result<VideoListInfoVO> getVideoListInfo() {
		return Result.success(videoService.getVideoListInfo());
	}

	@Permission(PermissionEnum.ADMIN)
	@GetMapping("/getVideoInfo")
	public Result<VideoInfoDTO> getVideoInfo(Integer id) {
		return Result.success(videoService.getVideoInfo(id));
	}

	@Limiter
	@Permission(PermissionEnum.USER)
	@GetMapping("/getVideoInfoByUser")
	public Result<UserVideoInfoVO> getVideoInfoByUser() throws CustomException {
		return Result.success(videoService.getVideoInfoByUser());
	}

	@Limiter
	@Permission(PermissionEnum.USER)
	@GetMapping("/doUserLike")
	public Result<Void> doUserLike(@NotNull(message = "id不能为空") String id) throws CustomException {
		videoService.doUserLike(id);
		return Result.success();
	}
}
