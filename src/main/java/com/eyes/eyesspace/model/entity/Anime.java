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
public class Anime implements Serializable {

    @Serial
    private static final long serialVersionUID = 4305038467898650906L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 动漫名称
     */
    private String title;

    /**
     * 类型
     */
    private String type;

    /**
     * 观看时期
     */
    private String period;

    /**
     * 介绍
     */
    private String introduce;

    /**
     * 站长说
     */
    private String word;

    /**
     * 封面链接
     */
    private String cover;

    /**
     * 点击量
     */
    private Integer view;

    /**
     * 0:正常, 1:私有, 2:删除
     */
    private String status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;
}
