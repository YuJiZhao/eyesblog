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
public class Music implements Serializable {

    @Serial
    private static final long serialVersionUID = 3882862792653762406L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String author;

    private String url;

    private String pic;

    private String lrc;

    private String ownerComment;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;

    private Integer views;
}
