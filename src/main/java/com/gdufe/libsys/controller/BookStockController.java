package com.gdufe.libsys.controller;


import com.gdufe.libsys.query.BookStockQuery;
import com.gdufe.libsys.service.BookStockService;
import com.gdufe.libsys.utils.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jonil
 * @since 2021-11-23
 */
@Controller
@RequestMapping("/bookStock")
public class BookStockController extends BaseController {


    @Resource
    private BookStockService bookStockService;

    //跳转到书籍检索页
    @GetMapping("/info")
    public String bookStockInfo(String isbn, HttpServletRequest request) {
        request.getSession().setAttribute("isbn", isbn);
        return "bookStock/book_stock";
    }

    //跳转到书籍库存页
    @GetMapping("/borrowInfo")
    public String borrowStockInfo(String isbn, HttpServletRequest request) {
        request.getSession().setAttribute("isbn", isbn);
        return "bookStock/borrow_stock";
    }

    //查询该isbn下的图书库存
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryBookStocksByParams(BookStockQuery bookStockQuery, HttpServletRequest request) {
        bookStockQuery.setIsbn((String) request.getSession().getAttribute("isbn"));
        return bookStockService.selectAll(bookStockQuery);
    }

    //跳转到增加库存页
    @GetMapping("toAddStock")
    public String toAddStock(String isbn, Model model) {
        return "bookStock/add_stock";
    }

    //增加库存
    @RequestMapping("addStock")
    @ResponseBody
    public ResultInfo addStock(HttpServletRequest request, Integer bookLocation, Integer amount) {
        String isbn = (String) request.getSession().getAttribute("isbn");
        bookStockService.addStock(isbn, amount, bookLocation);
        return new ResultInfo(200);
    }

    //跳转到减少库存页
    @GetMapping("toReduceStock")
    public String toReduceStock() {
        return "bookStock/reduce_stock";
    }

    //减少库存
    @RequestMapping("reduceStock")
    @ResponseBody
    public ResultInfo reduceStock(HttpServletRequest request, Integer bookLocation, Integer amount) {
        String isbn = (String) request.getSession().getAttribute("isbn");
        bookStockService.reduceStock(isbn, amount, bookLocation);
        return new ResultInfo(200);
    }

    //删除库存
    @RequestMapping("deleteStock")
    @ResponseBody
    public ResultInfo deleteStock(Integer bookId) {
        bookStockService.deleBook(bookId);
        return new ResultInfo(200);
    }

    //转移馆藏三水
    @PostMapping("/transferToSS")
    @ResponseBody
    public ResultInfo transferToSS(Integer[] ids) {
        bookStockService.transferToSS(ids);
        return new ResultInfo(200, "转移馆藏到三水成功");
    }

    //转移馆藏广州
    @PostMapping("/transferToGZ")
    @ResponseBody
    public ResultInfo transferToGZ(Integer[] ids) {
        bookStockService.transferToGZ(ids);
        return new ResultInfo(200, "转移图书到广州成功");
    }
}

