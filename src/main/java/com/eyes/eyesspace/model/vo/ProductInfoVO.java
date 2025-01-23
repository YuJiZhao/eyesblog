package com.eyes.eyesspace.model.vo;

import com.eyes.eyesspace.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductInfoVO {

    private String name;

    private String type;

    private String introduce;

    private String codeUrl;

    private String viewUrl;

    @JsonFormat(pattern = DateUtils.DATE_FORMAT)
    private LocalDateTime createTime;
}
