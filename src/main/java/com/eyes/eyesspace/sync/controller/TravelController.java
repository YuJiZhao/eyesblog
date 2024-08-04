package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.common.result.Result;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import com.eyes.eyesspace.sync.service.TravelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author artonyu
 * @date 2024-08-01 11:41
 */

@Api(tags = "旅行模块")
@RefreshScope
@Validated
@RestController
@RequestMapping("/travel")
public class TravelController {

    @Value("${business.comment.size:6}")
    private Integer commentSize;

    private final TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @ApiOperation("发表旅行评论")
    @Limiter
    @Permission(PermissionEnum.USER)
    @PostMapping("/doTravelComment")
    public Result<Void> doTravelComment(@Validated @RequestBody CommentAddRequest commentAddRequest) throws CustomException {
        travelService.doTravelComment(commentAddRequest);
        return Result.success();
    }

    @ApiOperation("获取旅行评论列表")
    @Limiter
    @Permission
    @GetMapping("/getTravelCommentList")
    public Result<List<CommentListVO>> getTravelCommentList(
            Integer id,
            @RequestParam(required = false) Integer page) throws CustomException {
        if (Objects.isNull(page) || page < 1) page = 1;
        return Result.success(travelService.getTravelCommentList(id, page, commentSize));
    }

    @ApiOperation("删除旅行评论")
    @Limiter
    @Permission(PermissionEnum.USER)
    @DeleteMapping("/delTravelComment/{id}")
    public Result<Void> delTravelComment(@PathVariable Integer id) throws CustomException {
        travelService.delTravelComment(id);
        return Result.success();
    }
}
