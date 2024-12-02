package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesspace.sync.common.exception.CustomException;
import com.eyes.eyesspace.sync.common.result.Result;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.AnimeInfoVO;
import com.eyes.eyesspace.sync.model.vo.AnimeListInfoVO;
import com.eyes.eyesspace.sync.model.vo.AnimeListVO;
import com.eyes.eyesspace.sync.model.vo.AnimeNoticeVO;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.service.AnimeService;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author eyesYeager
 * date 2023/5/21 19:44
 */

@RefreshScope
@Validated
@RestController
@RequestMapping("/anime")
public class AnimeController {
	@Value("${business.page-size.anime:6}")
	private Integer animePageSize;

	@Value("${business.comment.size:6}")
	private Integer commentSize;

	@Resource
	private AnimeService animeService;

	@Permission(PermissionEnum.ADMIN)
	@PostMapping("/uploadAnimePic")
	public Result<FileUploadVO> uploadAnimePic(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
		return Result.success(animeService.uploadAnimePic(multipartFile));
	}

	@Permission
	@Limiter
	@GetMapping("/getAnimeNotice")
	public Result<AnimeNoticeVO> getAnimeNotice() {
		return Result.success(animeService.getAnimeNotice());
	}

	@Permission
	@Limiter
	@GetMapping("/getAnimeListInfo")
	public Result<AnimeListInfoVO> getAnimeListInfo() {
		return Result.success(animeService.getAnimeListInfo());
	}

	@Permission
	@Limiter
	@GetMapping("/getAnimeList")
	public Result<AnimeListVO> getAnimeList(@RequestParam(required = false) Integer page) {
		page = (Objects.isNull(page) || page < 1) ? 1 : page;
		return Result.success(animeService.getAnimeList(page, animePageSize));
	}

	@Permission
	@Limiter
	@GetMapping("/getAnimeInfo/{id}")
	public Result<AnimeInfoVO> getAnimeInfo(@PathVariable Integer id) throws CustomException {
		return Result.success(animeService.getAnimeInfo(id));
	}

	@Limiter
	@Permission(PermissionEnum.USER)
	@PostMapping("/doAnimeComment")
	public Result<Void> doAnimeComment(@Validated @RequestBody CommentAddRequest commentAddRequest) throws CustomException {
		animeService.doAnimeComment(commentAddRequest);
		return Result.success();
	}

	@Limiter
	@Permission
	@GetMapping("/getAnimeCommentList")
	public Result<List<CommentListVO>> getAnimeCommentList(
			Integer id,
			@RequestParam(required = false) Integer page) throws CustomException {
		if (Objects.isNull(page) || page < 1) page = 1;
		return Result.success(animeService.getAnimeCommentList(id, page, commentSize));
	}

	@Limiter
	@Permission(PermissionEnum.USER)
	@DeleteMapping("/delAnimeComment/{id}")
	public Result<Void> delAnimeComment(@PathVariable Integer id) throws CustomException {
		animeService.delAnimeComment(id);
		return Result.success();
	}
}
