package com.eyes.eyesspace.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author eyesYeager
 * @since 2025-01-08
 */
@TableName("music_user")
public class MusicUser implements Serializable {

    @Serial
    private static final long serialVersionUID = -9117663471606089552L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private String userMusicKey;
}
