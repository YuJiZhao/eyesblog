package com.eyes.eyesspace.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author eyesYeager
 * @since 2025-01-08
 */

@Data
@TableName("footprint_content")
public class FootprintContent implements Serializable {

    @Serial
    private static final long serialVersionUID = 3927101829504523275L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer footprintId;

    private String type;

    private String content;

    private String timeFrom;

    private String timeTo;

    private Byte status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;
}
