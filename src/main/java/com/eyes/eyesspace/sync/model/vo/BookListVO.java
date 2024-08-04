package com.eyes.eyesspace.sync.model.vo;

import com.eyes.eyesspace.sync.model.dto.BookListDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author artonyu
 * @date 2024-08-01 10:03
 */

@Data
@ApiModel
@AllArgsConstructor
public class BookListVO {
    @ApiModelProperty("小说总数")
    private Integer total;

    @ApiModelProperty("小说列表数据")
    private List<BookListDTO> data;
}
