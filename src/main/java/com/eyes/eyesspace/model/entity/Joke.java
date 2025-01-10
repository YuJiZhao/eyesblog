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
public class Joke implements Serializable {

    @Serial
    private static final long serialVersionUID = 1379875554348287971L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String urlList;

    private Integer categoryId;

    private Boolean status;

    private LocalDateTime createTime;

    private LocalDateTime deleteTime;
}
