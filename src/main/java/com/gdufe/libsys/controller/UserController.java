package com.gdufe.libsys.controller;

import com.gdufe.libsys.entity.User;
import com.gdufe.libsys.query.UserQuery;
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
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    //用户登录
    @PostMapping("/login")
    @ResponseBody
    public ResultInfo login(HttpServletRequest request, String userId, String userPassword) {
        //登录
        userService.login(userId, userPassword);
        //设置session
        request.getSession().setAttribute("userId", userId);
        //返回操作结果
        return new ResultInfo(200);
    }

    //用户更新密码
    @PostMapping("/updatePassword")
    @ResponseBody
    public ResultInfo updateUserInfo(HttpServletRequest request, String oldPassword, String newPassword, String confirmPassword) {
        //更新密码操作
        userService.updateUserPassword(request.getSession().getAttribute("userId").toString(), oldPassword, newPassword, confirmPassword);
        //返回操作结果
        return new ResultInfo(200);
    }

    //更新用户信息
    @RequestMapping("/updateInfo")
    @ResponseBody
    public ResultInfo updateInfo(HttpServletRequest request, String username, String phone) {
        userService.updateInfo(request.getSession().getAttribute("userId").toString(), username, phone);
        return new ResultInfo(200);
    }

    //更新密码页
    @GetMapping("/toPasswordPage")
    public String toPasswordPage() {
        return "user/password";
    }

    //查询用户列表
    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> queryBookInfosByParams(UserQuery userQuery) {
        return userService.queryUsersByParams(userQuery);
    }

    //增加用户
    @RequestMapping("/addUser")
    @ResponseBody
    ResultInfo addUser(String userId, String userName, String userPassword, String phone, Integer identity) {
        userService.addUser(userId, userName, userPassword, phone, identity);
        return new ResultInfo(200);
    }

    //更新用户信息
    @RequestMapping("/updateUser")
    @ResponseBody
    ResultInfo updateUser(String userId, String userName, String userPassword, String phone, Integer identity, Integer status) {
        userService.updateUser(userId, userName, userPassword, phone, identity, status);
        return new ResultInfo(200);
    }

    //跳转到更新用户信息
    @RequestMapping("/toInfoPage")
    public String toInfoPage(HttpServletRequest request) {
        User user = userService.getById(request.getSession().getAttribute("userId").toString());
        request.getSession().setAttribute("user", user);
        return "user/Info";
    }

    //跳转到用户管理页
    @RequestMapping("/toManagePage")
    public String toManagePage() {
        return "user/manage";
    }

    //跳转到增加用户页
    @RequestMapping("/toAddUserPage")
    public String toAddUserPage() {
        return "user/add_user";
    }

    //跳转到更新用户信息页
    @RequestMapping("/toUpdateUserPage")
    public String toUpdateUserPage(HttpServletRequest request, String userId) {
        User user = userService.getById(userId);
        request.setAttribute("user", user);
        return "user/update_user";
    }

    //跳转到挂失页面
    @RequestMapping("/toLossPage")
    public String toLossPage() {
        return "user/loss_page";
    }

    //挂失接口
    @RequestMapping("/lossApply")
    @ResponseBody
    public ResultInfo lossApply(HttpServletRequest request, String password) {
        userService.lossApply(request.getSession().getAttribute("userId").toString(), password);
        return new ResultInfo(200);
    }

}

