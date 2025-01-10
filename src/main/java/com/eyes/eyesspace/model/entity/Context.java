package com.eyes.eyesspace.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author eyesYeager
 * @since 2025-01-08
 */

@Data
public class Context implements Serializable {

    @Serial
    private static final long serialVersionUID = -1585363599585969507L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String value;
}
