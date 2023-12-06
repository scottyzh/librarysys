package com.gdufe.libsys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdufe.libsys.component.Statistic;
import com.gdufe.libsys.entity.Borrow;
import com.gdufe.libsys.entity.Reserve;
import com.gdufe.libsys.entity.User;
import com.gdufe.libsys.mapper.BorrowMapper;
import com.gdufe.libsys.mapper.ReserveMapper;
import com.gdufe.libsys.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private BorrowMapper borrowMapper;

    @Resource
    private ReserveMapper reserveMapper;

    //用户登录页
    @GetMapping(value = {"index", "/"})
    public String index() {
        return "index";
    }

    //系统欢迎页
    @GetMapping("welcome")
    public String welcome(HttpServletRequest request) {
        String userId = request.getSession().getAttribute("userId").toString();
        User user = userService.getById(userId);

        //设置显示主页的信息
        request.setAttribute("user", user);
        String[] identity = {"学生", "老师", "图书管理员", "系统管理员"};
        String[] status = {"正常", "挂失", "注销", "暂停借阅"};
        request.setAttribute("identity", identity[user.getIdentity()]);
        request.setAttribute("status", status[user.getStatus()]);
        request.setAttribute("datetime", DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒").format(LocalDateTime.now()));
        request.setAttribute("logintimes", user.getPwErrortimes() + 1);
        //查询本月借阅次数
        List<Borrow> borrowList = borrowMapper.selectList(new QueryWrapper<Borrow>().ge("borrow_time", LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth())).le("borrow_time", LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())).eq("reader_id", userId));
        //查询本月预约次数
        List<Reserve> reserveList = reserveMapper.selectList(new QueryWrapper<Reserve>().ge("reserve_time", LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth())).le("reserve_time", LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())).eq("reader_id", userId));
        request.setAttribute("borrowtimes", borrowList.size());
        request.setAttribute("reservetimes", reserveList.size());
        request.setAttribute("dayOfMonth", LocalDateTime.now().toString().substring(8, 10));
        //图书馆信息
        request.setAttribute("libBorrowTimes", Statistic.getLibBorrowTimes());
        request.setAttribute("libReverseTimes", Statistic.getLibReverseTimes());
        request.setAttribute("libLoginTimes", Statistic.getLibLoginTimes());

        Double fine = userService.fineOfUser(userId);
        request.setAttribute("fine", fine);
        return "welcome";
    }

    //系统欢迎页
    @GetMapping("dashboard")
    public String dashboard(HttpServletRequest request) {
        String userId = request.getSession().getAttribute("userId").toString();
        User user = userService.getById(userId);
        request.setAttribute("user", user);
        String[] identity = {"学生", "老师", "图书管理员", "系统管理员"};
        String[] status = {"正常", "挂失", "注销", "暂停借阅"};
        request.setAttribute("identity", identity[user.getIdentity()]);
        request.setAttribute("status", status[user.getStatus()]);
        request.setAttribute("datetime", DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒").format(LocalDateTime.now()));
        request.setAttribute("logintimes", user.getPwErrortimes() + 1);

        //查询本月借阅次数
        List<Borrow> borrowList = borrowMapper.selectList(new QueryWrapper<Borrow>().ge("borrow_time", LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth())).le("borrow_time", LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())).eq("reader_id", userId));

        //查询本月预约次数
        List<Reserve> reserveList = reserveMapper.selectList(new QueryWrapper<Reserve>().ge("reserve_time", LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth())).le("reserve_time", LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())).eq("reader_id", userId));
        request.setAttribute("borrowtimes", borrowList.size());
        request.setAttribute("reservetimes", reserveList.size());
        request.setAttribute("dayOfMonth", LocalDateTime.now().toString().substring(8, 10));

        //图书馆信息
        request.setAttribute("libBorrowTimes", Statistic.getLibBorrowTimes());
        request.setAttribute("libReverseTimes", Statistic.getLibReverseTimes());
        request.setAttribute("libLoginTimes", Statistic.getLibLoginTimes());

        Double fine = userService.fineOfUser(userId);
        request.setAttribute("fine", fine);
        return "welcomeAdmin";
    }

    //后台管理页
    @GetMapping("main")
    public String admin(HttpServletRequest request) {
        String userId = request.getSession().getAttribute("userId").toString();
        User user = userService.getById(userId);
        request.setAttribute("user", user);
        if (user.getIdentity().equals(2)) {
            return "main_libraryAdmin";
        } else if (user.getIdentity().equals(0) || user.getIdentity().equals(1)) {
            return "main_reader";
        } else if (user.getIdentity().equals(3)) {
            return "main_sysAdmin";
        }
        return "main_reader";
    }
}
