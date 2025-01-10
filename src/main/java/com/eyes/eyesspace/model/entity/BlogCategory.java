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
@TableName("blog_category")
public class BlogCategory implements Serializable {

    @Serial
    private static final long serialVersionUID = -6993933175120668727L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String category;
}
