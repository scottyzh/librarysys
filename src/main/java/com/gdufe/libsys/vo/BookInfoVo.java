package com.gdufe.libsys.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BookInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * isbn
     */
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
     * 0正常，1暂停借阅
     */
    private Integer status;

    /**
     * 0三水，1广州，2三水广州
     */
    private Integer bookLocation;

    /**
     * 类别号
     */
    private String categoryName;

    /**
     * 入库时间
     */
    private LocalDateTime enterTime;

    /**
     * 图书图片
     */
    private String bookPic;

    /**
     * 图书简介
     */
    private String bookDescription;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
