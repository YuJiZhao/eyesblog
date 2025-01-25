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
@TableName("version_pic")
public class VersionPic implements Serializable {

    @Serial
    private static final long serialVersionUID = -8448437295890772198L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 版本id
     */
    private Integer versionId;

    /**
     * 图片url
     */
    private String url;

    private LocalDateTime createTime;

    private LocalDateTime deleteTime;
}
