package com.eyes.eyesspace.service.entertain.module.music.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel
public class MusicAddDto {
    @ApiModelProperty("id")
    private Integer id;
}
