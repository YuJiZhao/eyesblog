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
public class Footprint implements Serializable {

    @Serial
    private static final long serialVersionUID = -5222266158967368575L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String country;

    private String province;

    private String city;

    private Double latitude;

    private Double longitude;

    private Byte status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;
}
