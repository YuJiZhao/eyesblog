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
public class Home implements Serializable {

    @Serial
    private static final long serialVersionUID = 8054772454264721195L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 0: 博客, 1:说说, 2:版本，3:动漫
     */
    private Integer type;

    private Integer cid;

    private Integer status;

    private LocalDateTime createTime;
}
