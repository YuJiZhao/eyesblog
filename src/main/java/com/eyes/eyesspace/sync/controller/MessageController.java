package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesspace.sync.common.exception.CustomException;
import com.eyes.eyesspace.sync.common.result.Result;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.service.MessageService;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RefreshScope
@Validated
@RestController
@RequestMapping("/message")
public class MessageController {
	@Value("${business.comment.size:6}")
	private Integer commentSize;

	@Resource
	private MessageService messageService;

	@Limiter
	@Permission(PermissionEnum.USER)
	@PostMapping("/doMessage")
	public Result<Void> doMessage(@Validated @RequestBody CommentAddRequest commentAddRequest) throws CustomException {
		messageService.doMessage(commentAddRequest);
		return Result.success();
	}

	@Limiter
	@Permission
	@GetMapping("/getMessageList")
	public Result<List<CommentListVO>> getMessageList(
			Integer id,
			@RequestParam(required = false) Integer page) throws CustomException {
		if (Objects.isNull(page) || page < 1) page = 1;
		return Result.success(messageService.getMessageList(id, page, commentSize));
	}

	@Limiter
	@Permission(PermissionEnum.USER)
	@DeleteMapping("/delMessage/{id}")
	public Result<Void> delMessage(@PathVariable Integer id) throws CustomException {
		messageService.delMessage(id);
		return Result.success();
	}
}