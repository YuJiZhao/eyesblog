package com.eyes.eyesspace.sync.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author artonyu
 * @date 2024-08-05 09:38
 */
@Data
@ApiModel
public class TrackPointAddRequest {
    @ApiModelProperty("当前浏览器id")
    private String browserId;

    @ApiModelProperty("当前会话id")
    private String sessionId;

    @ApiModelProperty("埋点主题")
    private String title;

    @ApiModelProperty("埋点内容")
    private String content;

    @ApiModelProperty("当前url")
    private String path;
}
