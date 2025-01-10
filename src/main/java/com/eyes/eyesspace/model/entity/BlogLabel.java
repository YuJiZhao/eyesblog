package com.eyes.eyesspace.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author eyesYeager
 * @since 2025-01-08
 */

@Data
@TableName("blog_label")
public class BlogLabel implements Serializable {

    @Serial
    private static final long serialVersionUID = 5728711970578969948L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String label;
}
