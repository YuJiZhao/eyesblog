package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesspace.exception.BizException;
import com.eyes.eyesspace.result.PageBind;
import com.eyes.eyesspace.result.Result;
import com.eyes.eyesspace.model.vo.*;
import com.eyes.eyesspace.service.IAnimeService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author eyesYeager
 * date 2023/5/21 19:44
 */

@Validated
@RestController
@RequestMapping("/anime")
public class AnimeController {
	@Resource
	private IAnimeService animeService;

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/uploadAnimePic")
	public Result<FileUploadVO> uploadAnimePic(@RequestPart("file") MultipartFile multipartFile) throws BizException {
		return Result.success(animeService.uploadAnimePic(multipartFile));
	}

	@Permission
	@GetMapping("/getAnimeListInfo")
	public Result<AnimeListInfoVO> getAnimeListInfo() {
		return Result.success(animeService.getAnimeListInfo());
	}

	@Permission
	@GetMapping("/getAnimeList")
	public Result<PageBind<AnimeListVO>> getAnimeList(Integer page) {
		return Result.success(animeService.getAnimeList(page));
	}

	@Permission
	@GetMapping("/getAnimeInfo/{id}")
	public Result<AnimeInfoVO> getAnimeInfo(@PathVariable Integer id) throws BizException {
		return Result.success(animeService.getAnimeInfo(id));
	}
}
