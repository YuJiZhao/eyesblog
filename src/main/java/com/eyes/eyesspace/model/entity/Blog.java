package com.eyes.eyesspace.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author eyesYeager
 * @since 2025-01-08
 */

@Data
public class Blog implements Serializable {

    @Serial
    private static final long serialVersionUID = -2301063114166576027L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String summary;

    private String content;

    private Integer categoryId;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;

    private Integer words;

    private Integer views;
}
