package com.gdufe.libsys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdufe.libsys.entity.BookInfo;
import com.gdufe.libsys.query.BookInfoQuery;
import com.gdufe.libsys.vo.BookInfoVo;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jonil
 * @since 2021-11-23
 */
public interface BookInfoService extends IService<BookInfo> {

    //查询图书
    Map<String, Object> queryBookInfosByParams(BookInfoQuery bookInfoQuery);

    void addBookInfo(String isbn, String bookName, String author, String publisher, Integer categoryId, String bookPic, String bookDescription);

    void updateBookInfo(String isbn, String bookName, String author, String publisher, Integer categoryId);

    void stopBookBorrw(String isbn);

    BookInfoVo findDetail(String isbn);


}
