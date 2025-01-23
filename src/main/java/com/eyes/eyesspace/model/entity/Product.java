package com.eyes.eyesspace.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author eyesYeager
 * @since 2025-01-23
 */
@Data
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = -4672520044680735305L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名字
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 介绍
     */
    private String introduce;

    /**
     * 仓库地址
     */
    private String codeUrl;

    /**
     * 展示地址
     */
    private String viewUrl;

    /**
     * 0:正常, 1:私有, 2:删除
     */
    private Byte status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;
}
