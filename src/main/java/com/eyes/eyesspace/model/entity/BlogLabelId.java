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
@TableName("blog_label_id")
public class BlogLabelId implements Serializable {

    @Serial
    private static final long serialVersionUID = 3219408198128891322L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer blogId;

    private Integer labelId;
}
