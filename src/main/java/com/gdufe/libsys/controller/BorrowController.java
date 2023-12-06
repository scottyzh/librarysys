package com.gdufe.libsys.controller;


import com.gdufe.libsys.query.BookStockQuery;
import com.gdufe.libsys.query.BorrowQuery;
import com.gdufe.libsys.service.BookStockService;
import com.gdufe.libsys.service.BorrowService;
import com.gdufe.libsys.utils.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/borrow")
public class BorrowController extends BaseController {

    @Resource
    private BorrowService borrowService;

    @Resource
    private BookStockService bookStockService;

    //预约接口
    @RequestMapping("/book")
    @ResponseBody
    public ResultInfo book(HttpServletRequest request, String isbn) {
        borrowService.book(request.getSession().getAttribute("userId").toString(), isbn);
        return new ResultInfo(200);
    }

    //借阅表查询
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> queryBorrowByParams(BorrowQuery borrowQuery) {
        return borrowService.queryBorrowsByParams(borrowQuery);
    }

    //加载库存表
    @GetMapping("/toStock")
    public String toStock(HttpServletRequest request, String isbn, String readerId) {
        request.getSession().setAttribute("isbn", isbn);
        request.getSession().setAttribute("readerId", readerId);
        request.getSession().setAttribute("userId", request.getSession().getAttribute("userId"));
        return "borrow/borrow_process";
    }

    //查询该isbn下的图书库存
    @GetMapping("/book_stock")
    @ResponseBody
    public Map<String, Object> queryBookStocksByParams(HttpServletRequest request, BookStockQuery bookStockQuery) {
        bookStockQuery.setIsbn(request.getSession().getAttribute("isbn").toString());
        return bookStockService.selectAll(bookStockQuery);
    }

    //查询该isbn下的图书库存(处理预约按钮对应的
    @RequestMapping("/borrowList")
    @ResponseBody
    public Map<String, Object> queryBorrowStocksByParams(HttpServletRequest request, BookStockQuery bookStockQuery) {
        bookStockQuery.setIsbn(request.getSession().getAttribute("isbn").toString());
        return bookStockService.selectAll(bookStockQuery);
    }

    //借书
    @RequestMapping("/borrow_book")
    @ResponseBody
    public ResultInfo borrowBook(HttpServletRequest request, Integer bookId) {
        borrowService.borrow(request.getSession().getAttribute("readerId").toString(), bookId, request.getSession().getAttribute("userId").toString());
        return new ResultInfo(200);
    }

    //归还图书
    @RequestMapping("/giveback")
    @ResponseBody
    public ResultInfo giveback(Integer borrowId) {
        borrowService.giveback(borrowId);
        return new ResultInfo(200);
    }

    //催还图书
    @RequestMapping("/urgereturn")
    @ResponseBody
    public ResultInfo urgereturn(Integer borrowId) {
        borrowService.urgereturn(borrowId);
        return new ResultInfo(200);
    }

    //借阅管理页面
    @RequestMapping("/toManagePage")
    public String toManagePage() {
        return "/borrow/borrow_list";
    }

    //去管理员借阅界面
    @RequestMapping("/toBorrowPage")
    public String toBorrowPage() {
        return "/borrow/borrow";
    }

    //借书、预约页
    @RequestMapping("/toBookPage")
    public String toBookPidsage() {
        return "/borrow/book";
    }

    @RequestMapping("/toMyBorrowPage")
    public String toMyBorrowPage(HttpServletRequest request) {
        request.setAttribute("user", request.getAttribute("user"));
        return "borrow/my_borrow_list";
    }

    //读者个人借阅表查询
    @RequestMapping("/my_borrow_list")
    @ResponseBody
    public Map<String, Object> queryMyBorrowByParams(HttpServletRequest request, BorrowQuery borrowQuery) {
        borrowQuery.setReaderId(request.getSession().getAttribute("userId").toString());
        return borrowService.queryBorrowsByParams(borrowQuery);
    }

    //读者个人借阅表查询
    @RequestMapping("/my_fine_list")
    @ResponseBody
    public Map<String, Object> queryMyFineByParams(HttpServletRequest request, BorrowQuery borrowQuery) {
        borrowQuery.setReaderId(request.getSession().getAttribute("userId").toString());
        borrowQuery.setFine(1);
        return borrowService.queryBorrowsByParams(borrowQuery);
    }

    @RequestMapping("/renew")
    @ResponseBody
    public ResultInfo renewBorrow(Integer borrowId) {
        borrowService.renewBorrow(borrowId);
        return new ResultInfo(200);
    }

    //查看罚款历史
    @RequestMapping("/toFinePage")
    public String toFinePage() {
        return "borrow/fine_list";
    }


}

