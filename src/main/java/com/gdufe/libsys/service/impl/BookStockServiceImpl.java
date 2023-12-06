package com.gdufe.libsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdufe.libsys.entity.BookStock;
import com.gdufe.libsys.mapper.BookStockMapper;
import com.gdufe.libsys.query.BookStockQuery;
import com.gdufe.libsys.service.BookStockService;
import com.gdufe.libsys.utils.AssertUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jonil
 * @since 2021-11-23
 */
@Service
public class BookStockServiceImpl extends ServiceImpl<BookStockMapper, BookStock> implements BookStockService {

    @Resource
    private BookStockMapper bookStockMapper;

    @Override
    public Map<String, Object> selectAll(BookStockQuery bookStockQuery) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<BookStock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isbn", bookStockQuery.getIsbn());
        if (bookStockQuery.getBookId() != null) {
            queryWrapper.eq("book_id", bookStockQuery.getBookId());
        }
        if (bookStockQuery.getStatus() != null) {
            queryWrapper.eq("status", bookStockQuery.getStatus());
        }
        if (bookStockQuery.getBookLocation() != null) {
            queryWrapper.eq("book_location", bookStockQuery.getBookLocation());
        }
        PageHelper.startPage(bookStockQuery.getPage(), bookStockQuery.getLimit());
        PageInfo<BookStock> pageInfo = new PageInfo<>(bookStockMapper.selectList(queryWrapper));
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    //添加库存
    @Override
    public void addStock(String isbn, int bookAmount, int bookLocation) {
        for (int i = 0; i < bookAmount; i++) {
            BookStock bookStock = new BookStock();
            bookStock.setIsbn(isbn);
            bookStock.setBookLocation(bookLocation);
            bookStock.setStatus(0);
            bookStock.setEnterTime(LocalDateTime.now());
            bookStockMapper.insert(bookStock);
        }
    }

    //减少库存
    @Override
    public void reduceStock(String isbn, int bookAmount, int bookLocation) {
        QueryWrapper<BookStock> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("isbn", isbn).eq("book_location", bookLocation).eq("status", 0);
        List<BookStock> bookStocks = bookStockMapper.selectList(objectQueryWrapper);
        AssertUtil.isTrue(bookAmount > bookStocks.size(), "填入的出库数量大于现有库存");
        int i = 0;
        for (BookStock bookStock : bookStocks) {
            bookStock.setStatus(2);
            bookStock.setOutTime(LocalDateTime.now());
            bookStockMapper.updateById(bookStock);
            i++;
            if (i == bookAmount) {
                break;
            }
        }
    }

    @Override
    public void deleBook(Integer bookId) {
        BookStock bookStock = bookStockMapper.selectById(bookId);
        AssertUtil.isTrue(bookStock.getStatus() == 1, "该书处于借出状态！无法出库");
        bookStock.setStatus(2);
        bookStock.setOutTime(LocalDateTime.now());
        bookStockMapper.updateById(bookStock);
    }

    @Override
    public void transferToSS(Integer[] bookIds) {
        List<BookStock> bookStockList = bookStockMapper.selectBatchIds(Arrays.asList(bookIds));
        for (BookStock bookStock : bookStockList) {
            AssertUtil.isTrue(bookStock.getStatus() == 1, "该书处于借出状态！无法转移");
            AssertUtil.isTrue(bookStock.getStatus() == 2, "该书处于出库状态！无法转移");
            bookStock.setBookLocation(0);
        }
        updateBatchById(bookStockList);
    }

    @Override
    public void transferToGZ(Integer[] bookIds) {
        List<BookStock> bookStockList = bookStockMapper.selectBatchIds(Arrays.asList(bookIds));
        for (BookStock bookStock : bookStockList) {
            AssertUtil.isTrue(bookStock.getStatus() == 1, "该书处于借出状态！无法转移");
            AssertUtil.isTrue(bookStock.getStatus() == 2, "该书处于出库状态！无法转移");
            bookStock.setBookLocation(1);
        }
        updateBatchById(bookStockList);
    }
}
