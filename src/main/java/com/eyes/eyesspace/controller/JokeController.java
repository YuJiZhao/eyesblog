package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.common.result.PageBind;
import com.eyes.eyesspace.common.result.Result;
import com.eyes.eyesspace.model.dto.JokeListDTO;
import com.eyes.eyesspace.model.request.JokeAddRequest;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.model.vo.JokeAddVO;
import com.eyes.eyesspace.service.IJokeService;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author eyesYeager
 * date 2023/9/25 8:42
 */

@Validated
@RestController
@RequestMapping("/joke")
public class JokeController {

	@Resource
	private IJokeService jokeService;

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/addJoke")
	public Result<JokeAddVO> addJoke(@Validated @RequestBody JokeAddRequest jokeAddRequest) throws CustomException {
		if (CollectionUtils.isEmpty(jokeAddRequest.getUrlList())) {
			throw new CustomException("梗图图片链接不能为空");
		}
		return Result.success(jokeService.addJoke(jokeAddRequest));
	}

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/uploadJokePic")
	public Result<FileUploadVO> uploadJokePic(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
		return Result.success(jokeService.uploadJokePic(multipartFile));
	}

	@Permission
	@Limiter
	@GetMapping("/getJokeList")
	public Result<PageBind<JokeListDTO>> getJokeList(Integer page) {
		return Result.success(jokeService.getJokeList(page));
	}
}