package com.gdufe.libsys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Reserve implements Serializable {

    /**
     * 自增ID
     */
    @TableId(value = "reserve_id", type = IdType.AUTO)
    private Integer reserveId;

    /**
     * 图书isbn
     */
    private String isbn;

    /**
     * 读者ID
     */
    private String readerId;

    /**
     * 读者身份
     */
    private Integer readerIdentity;

    /**
     * 预约时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime reserveTime;

    /**
     * 预约状态
     */
    private Integer status;
}