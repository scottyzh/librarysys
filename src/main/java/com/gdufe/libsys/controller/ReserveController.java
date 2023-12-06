package com.gdufe.libsys.controller;


import com.gdufe.libsys.entity.User;
import com.gdufe.libsys.query.BookStockQuery;
import com.gdufe.libsys.query.ReserveQuery;
import com.gdufe.libsys.service.BookStockService;
import com.gdufe.libsys.service.ReserveService;
import com.gdufe.libsys.service.UserService;
import com.gdufe.libsys.utils.ResultInfo;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/reserve")
public class ReserveController extends BaseController {

    @Resource
    private ReserveService reserveService;
    @Resource
    private BookStockService bookStockService;
    @Resource
    private UserService userService;

    //跳转到预约表
    @RequestMapping("/toReserve")
    public String toReserve() {
        return "/reserve/reserve_list";
    }

    //加载预约列表
    @GetMapping("/reserveList")
    @ResponseBody
    public Map<String, Object> queryReserveListByParams(ReserveQuery reserveQuery) {
        Map<String, Object> stringObjectMap = reserveService.queryReserveListByParams(reserveQuery);
        return stringObjectMap;
    }


    //加载库存表
    @GetMapping("/toStock")
    public String toStock(String isbn, String readerId, Integer reserveId, HttpServletRequest request) {
        request.getSession().setAttribute("isbn", isbn);
        request.getSession().setAttribute("readerId", readerId);
        request.getSession().setAttribute("reserveId", reserveId);
        return "reserve/reserve_process";
    }

    //查询该isbn下的图书库存(处理预约按钮对应的）
    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> queryBookStocksByParams(HttpServletRequest request, BookStockQuery bookStockQuery) {
        bookStockQuery.setIsbn( (String) request.getSession().getAttribute("isbn"));
        return bookStockService.selectAll(bookStockQuery);
    }

    //选择图书
    @PostMapping("/selectBook")
    @ResponseBody
    public ResultInfo selectBookById(HttpServletRequest request, Integer bookId) {
        reserveService.selectBookById(bookId, (String) request.getSession().getAttribute("readerId"), (Integer) request.getSession().getAttribute("reserveId"), request.getSession().getAttribute("userId").toString());
        return new ResultInfo(200);
    }

    //预约书籍
    @RequestMapping("/book")
    @ResponseBody
    public ResultInfo reserveBook(HttpServletRequest request, String isbn) {
        reserveService.book(request.getSession().getAttribute("userId").toString(), isbn);
        return new ResultInfo(200);
    }

    //提醒取书
    @RequestMapping("/remind")
    @ResponseBody
    public ResultInfo remindBook(Integer reserveId) {
        reserveService.remindBook(reserveId);
        return new ResultInfo(200);
    }

    //跳转到预约表(读书者下的）
    @RequestMapping("/toReaderReserve")
    public String toReaderReserve(HttpServletRequest request) {
        return "/reserve/reserve_readerList";
    }


    //加载预约列表(读者下的）
    @GetMapping("/reserveReaderList")
    @ResponseBody
    public Map<String, Object> queryReserveReaderListByParams(ReserveQuery reserveQuery, HttpServletRequest request) {
        User user = userService.getById(request.getSession().getAttribute("userId").toString());
        if ((user.getIdentity() != 2 || user.getIdentity() != 3)){
            reserveQuery.setReaderId(user.getUserId());
        }
        return reserveService.queryReserveListByParams(reserveQuery);
    }

    //读者取消预约
    @RequestMapping("/cancel")
    @ResponseBody
    public ResultInfo cancelReserve(Integer reserveId) {
        reserveService.cancelReserve(reserveId);
        return new ResultInfo(200);
    }
}

