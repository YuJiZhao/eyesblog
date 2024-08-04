package com.eyes.eyesspace.sync.model.dto;

import com.eyes.eyesspace.sync.model.bean.HomeListBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author artonyu
 * @date 2024-08-01 10:04
 */

@Data
@ApiModel
public class BookListDTO implements HomeListBean {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("小说名称")
    private String title;

    @ApiModelProperty("小说类型")
    private String type;

    @ApiModelProperty("阅读时期")
    private String period;

    @ApiModelProperty("介绍")
    private String introduce;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("点击量")
    private Integer view;

    @ApiModelProperty("评论数")
    private Integer comment;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;
}
