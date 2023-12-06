package com.gdufe.libsys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdufe.libsys.entity.BookStock;
import com.gdufe.libsys.query.BookStockQuery;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jonil
 * @since 2021-11-23
 */
public interface BookStockService extends IService<BookStock> {

    Map<String, Object> selectAll(BookStockQuery bookStockQuery);

    void addStock(String isbn, int bookAmount, int bookLocation);

    void reduceStock(String isbn, int bookAmount, int bookLocation);

    void deleBook(Integer bookId);

    void transferToSS(Integer[] bookIds);

    void transferToGZ(Integer[] bookIds);
}
