package com.gdufe.libsys.controller;


import com.gdufe.libsys.query.UserMsgQuery;
import com.gdufe.libsys.service.UserMsgService;
import org.springframework.stereotype.Controller;
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
 * @since 2021-12-03
 */
@Controller
@RequestMapping("/userMsg")
public class UserMsgController {

    @Resource
    private UserMsgService userMsgService;

    //获取当前用户的所有信息
    @RequestMapping("/getMsg")
    @ResponseBody
    public Map<String, Object> getMsg(HttpServletRequest request) {
        UserMsgQuery userMsgQuery = new UserMsgQuery();
        userMsgQuery.setUserId(request.getSession().getAttribute("userId").toString());
        return userMsgService.getMsg(userMsgQuery);
    }
}

