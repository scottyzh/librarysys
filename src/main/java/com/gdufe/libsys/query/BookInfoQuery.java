package com.gdufe.libsys.query;


import lombok.Data;

@Data
public class BookInfoQuery extends BaseQuery {
    //isbn
    private String isbn;
    //书名
    private String bookName;
    //作者
    private String author;
    //出版社
    private String publisher;
}
