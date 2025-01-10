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
@TableName("joke_category")
public class JokeCategory implements Serializable {

    @Serial
    private static final long serialVersionUID = 8411790554202458446L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String category;

    private LocalDateTime createTime;

    private LocalDateTime deleteTime;
}
