package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesspace.result.PageBind;
import com.eyes.eyesspace.result.Result;
import com.eyes.eyesspace.model.dto.JokeListDTO;
import com.eyes.eyesspace.service.IJokeService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@Permission
	@Limiter
	@GetMapping("/getJokeList")
	public Result<PageBind<JokeListDTO>> getJokeList(Integer page) {
		return Result.success(jokeService.getJokeList(page));
	}
}