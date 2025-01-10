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
public class Video implements Serializable {

    @Serial
    private static final long serialVersionUID = 4836146068944863878L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String originalAuthor;

    private String ownerComment;

    private String videoUrl;

    private String pictureUrl;

    private String originalUrl;

    /**
     * 0：正常、1：暂存、2：删除
     */
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;

    private Integer views;
}
