package com.eyes.eyesspace.sync.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author artonyu
 * @date 2024-08-01 10:01
 */
@Data
@ApiModel
public class BookListInfoVO {
    @ApiModelProperty("小说总数")
    private Long totalNum;

    @ApiModelProperty("公开小说数")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long publicNum;

    @ApiModelProperty("私有小说数")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long privateNum;

    @ApiModelProperty("已删除小说数")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long deleteNum;

    @ApiModelProperty("总点击量")
    private Integer clickNum;

    @ApiModelProperty("总评论数")
    private Integer commentNum;

    public BookListInfoVO(Integer clickNum, Integer commentNum) {
        this.clickNum = clickNum;
        this.commentNum = commentNum;
    }
}
