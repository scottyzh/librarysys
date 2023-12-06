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

/**
 * <p>
 *
 * </p>
 *
 * @author jonil
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BookStock implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图书Id
     */
    @TableId(value = "book_id", type = IdType.AUTO)
    private Integer bookId;


    /**
     * isbn
     */
    private String isbn;

    /**
     * 0, 未被借；1，被借
     */
    private Integer status;

    /**
     * 0三水，1广州
     */
    private Integer bookLocation;

    /**
     * 入库时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime enterTime;

    /**
     * 出库时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime outTime;

}
