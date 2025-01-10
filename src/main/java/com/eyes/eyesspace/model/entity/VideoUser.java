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
@TableName("video_user")
public class VideoUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 3721134861958258781L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private String userVideoKey;
}
