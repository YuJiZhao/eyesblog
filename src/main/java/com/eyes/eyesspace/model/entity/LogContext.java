package com.eyes.eyesspace.model.entity;

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
@TableName("log_context")
public class LogContext implements Serializable {

    @Serial
    private static final long serialVersionUID = -1313638901491460530L;

    private Integer id;

    private String name;

    private String content;

    private LocalDateTime createTime;
}
