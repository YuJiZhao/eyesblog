package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesspace.persistent.dto.MusicInfoDTO;
import com.eyes.eyesspace.persistent.dto.MusicListDTO;
import com.eyes.eyesspace.sync.common.exception.CustomException;
import com.eyes.eyesspace.sync.common.result.Result;
import com.eyes.eyesspace.sync.model.request.MusicAddRequest;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.vo.MusicAddVO;
import com.eyes.eyesspace.sync.model.vo.MusicListInfoVO;
import com.eyes.eyesspace.sync.model.vo.UserMusicInfoVO;
import com.eyes.eyesspace.sync.service.MusicService;

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

@RestController
@RefreshScope
@RequestMapping("/music")
@Validated
public class MusicController {
	@Value("${business.page-size.music:10}")
	private Integer musicPageSize;

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

	@Permission(PermissionEnum.ADMIN)
	@GetMapping("/getMusicList")
	public Result<List<MusicListDTO>> getMusicList(@RequestParam(required = false) Integer page) {
		page = (Objects.isNull(page) || page < 1) ? 1 : page;
		return Result.success(musicService.getMusicList(page, musicPageSize));
	}

	@Permission(PermissionEnum.ADMIN)
	@GetMapping("/getMusicListInfo")
	public Result<MusicListInfoVO> getMusicListInfo() {
		return Result.success(musicService.getMusicListInfo());
	}

	@Permission(PermissionEnum.ADMIN)
	@GetMapping("/getMusicInfo")
	public Result<MusicInfoDTO> getMusicInfo(Integer id) {
		return Result.success(musicService.getMusicInfo(id));
	}

	@Limiter
	@GetMapping("/getMusicLrc")
	public String getMusicLrc(@NotNull(message = "id不能为空") String id) throws CustomException {
		// 该方法由APlayer调用，无法自定义请求头，因此不做权限需求
		return musicService.getMusicLrc(id);
	}

	@Limiter
	@Permission(PermissionEnum.USER)
	@GetMapping("/getMusicInfoByUser")
	public Result<UserMusicInfoVO> getMusicInfoByUser() throws CustomException {
		return Result.success(musicService.getMusicInfoByUser());
	}

	@Limiter
	@Permission(PermissionEnum.USER)
	@GetMapping("/doUserLike")
	public Result<Void> doUserLike(@NotNull(message = "id不能为空") String id) throws CustomException {
		musicService.doUserLike(id);
		return Result.success();
	}
}
