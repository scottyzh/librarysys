package com.gdufe.libsys.query;


import lombok.Data;

@Data
public class ReserveQuery extends BaseQuery {
    //预约ID
    private Integer reserveId;
    //书籍ID
    private Integer bookId;
    //读者ID
    private String readerId;
    //isbn
    private String isbn;
    //读者身份
    private Integer readerIdentity;
    //读者状态
    private Integer status;
}
