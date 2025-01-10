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
public class Friend implements Serializable {

    @Serial
    private static final long serialVersionUID = 4700609265793014004L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 网站名称
     */
    private String name;

    /**
     * 网站介绍
     */
    private String introduce;

    /**
     * 头像链接
     */
    private String avatar;

    /**
     * 网站链接
     */
    private String address;

    /**
     * 0：正常，1：审核中，2：失效，3：删除，4：暂存
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 审核成功时间
     */
    private LocalDateTime successTime;

    /**
     * 判定失效时间
     */
    private LocalDateTime invalidTime;
}
