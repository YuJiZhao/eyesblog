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
public class Version implements Serializable {

    @Serial
    private static final long serialVersionUID = -3782715534825577995L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 版本号
     */
    private String version;

    /**
     * 0：整站，1：前端，2：后端
     */
    private Boolean type;

    /**
     * 描述
     */
    private String description;

    /**
     * 0：正常，1：删除
     */
    private Boolean status;

    private LocalDateTime createTime;
}
