package com.gdufe.libsys.controller;


import com.gdufe.libsys.entity.BookInfo;
import com.gdufe.libsys.query.BookInfoQuery;
import com.gdufe.libsys.service.BookInfoService;
import com.gdufe.libsys.utils.ResultInfo;
import com.gdufe.libsys.vo.BookInfoVo;
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
@RequestMapping("/book")
public class BookInfoController extends BaseController {

    @Resource
    private BookInfoService bookInfoService;

    //查询图书列表
    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> queryBookInfosByParams(BookInfoQuery bookInfoQuery) {
        return bookInfoService.queryBookInfosByParams(bookInfoQuery);
    }

    //跳转到图书页
    @GetMapping("/index")
    public String index() {
        return "book/book_info";
    }

    //跳转到增加图书
    @GetMapping("toAddBook")
    public String toAddBookPage() {
        return "book/add_book";
    }

    //跳转到图书详情页
    @GetMapping("toDetailPage")
    public String toDe(String isbn, HttpServletRequest request) {
        request.getSession().setAttribute("isbn", isbn);
        BookInfoVo bookInfoVo = bookInfoService.findDetail(isbn);
        request.getSession().setAttribute("bookInfoVo", bookInfoVo);
        return "book/book_detail";
    }

    //增加图书页接口
    @RequestMapping("/addBook")
    @ResponseBody
    ResultInfo addBook(String isbn, String bookName, String author, String publisher, Integer category, String bookPic, String bookDescription) {
        String a = bookPic;
        bookInfoService.addBookInfo(isbn, bookName, author, publisher, category, bookPic, bookDescription);
        return new ResultInfo(200);
    }

    //跳转到图书跟新信息页
    @RequestMapping("toUpdatePage")
    public String toUpdatePage(HttpServletRequest request, String isbn) {
        BookInfo bookInfo = bookInfoService.getById(isbn);
        request.setAttribute("bookInfo", bookInfo);
        return "book/update_book";
    }

    //图书信息更新接口
    @RequestMapping("/updateBook")
    @ResponseBody
    ResultInfo updateBook(String isbn, String bookName, String author, String publisher, Integer category) {
        int i = 0;
        bookInfoService.updateBookInfo(isbn, bookName, author, publisher, category);
        return new ResultInfo(200);
    }

    //停止借阅书籍
    @RequestMapping("/stopBorrowBook")
    @ResponseBody
    ResultInfo updateBook(String isbn) {
        bookInfoService.stopBookBorrw(isbn);
        return new ResultInfo(200);
    }




}

