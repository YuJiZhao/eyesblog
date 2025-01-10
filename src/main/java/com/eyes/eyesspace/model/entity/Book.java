package com.eyes.eyesspace.model.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author eyesYeager
 * @since 2025-01-08
 */

@Data
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 7248141810284897859L;

    private Integer id;

    /**
     * 书名
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 类型
     */
    private String type;

    /**
     * 阅读时期
     */
    private String period;

    /**
     * 书籍介绍
     */
    private String introduce;

    /**
     * 站长说
     */
    private String word;

    /**
     * 点击量
     */
    private String view;

    /**
     * 0:正常, 1:私有, 2:删除
     */
    private String status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;
}
