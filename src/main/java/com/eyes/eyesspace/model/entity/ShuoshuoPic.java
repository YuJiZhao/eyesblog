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
@TableName("shuoshuo_pic")
public class ShuoshuoPic implements Serializable {

    @Serial
    private static final long serialVersionUID = 5698629590380498284L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer shuoshuoId;

    private String url;

    private LocalDateTime createTime;

    private LocalDateTime deleteTime;
}
