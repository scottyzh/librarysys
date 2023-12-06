package com.gdufe.libsys.query;


import lombok.Data;

@Data
public class BorrowQuery extends BaseQuery {
    private Integer bookId;
    private String readerId;
    private Integer status;
    private String operator;
    private Integer fine;
    private Integer fineFin;
}
