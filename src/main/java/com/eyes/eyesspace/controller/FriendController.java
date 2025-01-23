package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesspace.result.Result;
import com.eyes.eyesspace.model.dto.FriendListDTO;
import com.eyes.eyesspace.model.vo.FriendListInfoVO;
import com.eyes.eyesspace.service.IFriendService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author eyesYeager
 * date 2023/5/31 11:48
 */

@Validated
@RestController
@RequestMapping("/friend")
public class FriendController {

	@Resource
	private IFriendService friendService;

	@Permission
	@GetMapping("/getFriendListData")
	public Result<FriendListInfoVO> getFriendListData() {
		return Result.success(friendService.getFriendListData());
	}

	@Permission
	@GetMapping("/getFriendList")
	public Result<List<FriendListDTO>> getFriendList() {
		return Result.success(friendService.getFriendList());
	}
}