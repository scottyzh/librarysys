package com.gdufe.libsys.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BookRankVo implements Serializable {

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
     * 三个月内借阅次数
     */
    private Integer borrowTimesInThreeMonths;

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
    private Integer categoryId;

    /**
     * 当前库存
     */
    private Integer presentStock;
}
