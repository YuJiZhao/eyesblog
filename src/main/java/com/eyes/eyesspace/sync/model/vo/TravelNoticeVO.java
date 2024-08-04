package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author artonyu
 * @date 2024-08-01 14:15
 */

@Data
@ApiModel
@AllArgsConstructor
public class TravelNoticeVO {
    @ApiModelProperty("站长说")
    private String notice;
}
