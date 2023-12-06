package com.gdufe.libsys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * @since 2021-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BookInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * isbn
     */
    @TableId(value = "isbn")
    private String isbn;

    /**
     * 书名
     */
    private String bookName;

    /**
     * 作者
     */
    private String author;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 总库存
     */
    private Integer totalStock;

    /**
     * 当前库存
     */
    private Integer presentStock;

    /**
     * 0正常，1暂停借阅,2出库
     */
    private Integer status;

    /**
     * 0三水，1广州，2三水广州,3未设定
     */
    private Integer bookLocation;

    /**
     * 图书图片
     */
    private String bookPic;

    /**
     * 图书简介
     */
    private String bookDescription;

    /**
     * 类别号
     */
    private Integer categoryId;

    /**
     * 入库时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime enterTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
