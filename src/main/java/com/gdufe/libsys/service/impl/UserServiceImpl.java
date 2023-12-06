package com.gdufe.libsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdufe.libsys.component.Statistic;
import com.gdufe.libsys.entity.Borrow;
import com.gdufe.libsys.entity.User;
import com.gdufe.libsys.entity.UserMsg;
import com.gdufe.libsys.enums.UserStatusEnum;
import com.gdufe.libsys.mapper.BorrowMapper;
import com.gdufe.libsys.mapper.UserMapper;
import com.gdufe.libsys.mapper.UserMsgMapper;
import com.gdufe.libsys.query.UserQuery;
import com.gdufe.libsys.service.UserService;
import com.gdufe.libsys.utils.AssertUtil;
import com.gdufe.libsys.utils.Md5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jonil
 * @since 2021-11-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private BorrowMapper borrowMapper;

    @Resource
    private UserMsgMapper userMsgMapper;

    //用户登录
    @Override
    public void login(String userId, String userPassword) {
        //数据库查询用户信息
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(null == user, "用户或密码错误！");
        LocalDateTime loginTime = user.getLoginTime();
        if (loginTime != null) {
            Date borrowT = Date.from(loginTime.atZone(ZoneId.systemDefault()).toInstant());
            Date currentT = new Date();
            long c = currentT.getTime();
            long b = borrowT.getTime();
            long millis = c - b;
            int lastLoginDay;
            lastLoginDay = (int) TimeUnit.MILLISECONDS.toDays(millis) - 1;
            if (lastLoginDay > 0) {
                user.setPwErrortimes(0);
                userMapper.updateById(user);
            }
        }
        AssertUtil.isTrue(user.getPwErrortimes() >= 5, "当天输入密码错误次数达到上限！");
        //检查密码是否错误
        if (!(user.getUserPassword().equals(Md5Util.encode(userPassword)))) {
            Integer pwErrortimes = user.getPwErrortimes();
            pwErrortimes++;
            user.setPwErrortimes(pwErrortimes);
            user.setLoginTime(LocalDateTime.now());
            userMapper.updateById(user);
        }
        AssertUtil.isTrue(!(user.getUserPassword().equals(Md5Util.encode(userPassword))), "用户或密码错误！");
        Statistic.login();
    }

    //修改用户密码
    @Override
    public void updateUserPassword(String userId, String userOldPassword, String newPassword, String confirmPassword) {

        //判断更新的数据是否为空值
        AssertUtil.isTrue("".equals(userOldPassword), "旧密码不能为空！");
        AssertUtil.isTrue("".equals(newPassword), "新密码不能为空！");
        AssertUtil.isTrue("".equals(confirmPassword), "确认密码不能为空！");
        //判断确认密码是否一致
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)), "确认密码与新密码不一致！");
        //如果新旧密码一致
        AssertUtil.isTrue(newPassword.equals(userOldPassword), "新旧密码一致，不必更新哦！");
        //没有携带userId则是未登录
        AssertUtil.isTrue(null == userId, "用户未登录！");
        //查看用户是否存在
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(null == user, "用户不存在！");
        //对比用户旧密码
        AssertUtil.isTrue(!(user.getUserPassword().equals(Md5Util.encode(userOldPassword))), "旧密码错误！");
        //进行更新密码操作
        user.setUserPassword(Md5Util.encode(newPassword));
        userMapper.updateById(user);
    }

    //修改信息
    @Override
    public void updateInfo(String userId, String username, String phone) {
        User user = userMapper.selectById(userId);
        user.setUserName(username);
        user.setPhone(phone);
        userMapper.updateById(user);
    }

    //添加用户
    @Override
    public void addUser(String userId, String userName, String userPassword, String phone, Integer identity) {
        //生成用户对象并补全信息
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserPassword(Md5Util.encode(userPassword));
        user.setPhone(phone);
        user.setIdentity(identity);
        user.setCreateTime(LocalDateTime.now());
        AssertUtil.isTrue(userMapper.insert(user) < 1, 201, "添加用户失败");
    }

    //更新用户信息
    @Override
    public void updateUser(String userId, String userName, String userPassword, String phone, Integer identity, Integer status) {
        User user = userMapper.selectById(userId);
        user.setUserName(userName);
        user.setIdentity(identity);
        if (userPassword != null && !"".equals(userPassword)) {
            user.setUserPassword(Md5Util.encode(userPassword));
        }
        user.setStatus(status);
        userMapper.updateById(user);
    }


    //挂失
    @Override
    public void lossApply(String userId, String password) {
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(user == null, "挂失失败，请联系图书管理员！");
        AssertUtil.isTrue(!user.getUserPassword().equals(Md5Util.encode(password)), "密码错误，挂失失败，请稍后再试！");
        user.setStatus(UserStatusEnum.挂失.getCode());
        userMapper.updateById(user);
    }

    @Override
    public Map<String, Object> queryUsersByParams(UserQuery userQuery) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (userQuery.getUserId() != null && !Objects.equals(userQuery.getUserId(), "")) {
            queryWrapper.eq("user_id", userQuery.getUserId());
        }
        if (userQuery.getUserName() != null && !Objects.equals(userQuery.getUserName(), "")) {
            queryWrapper.like("user_name", userQuery.getUserName());
        }
        if (userQuery.getStatus() != null && !Objects.equals(userQuery.getStatus(), "")) {
            queryWrapper.eq("status", userQuery.getStatus());
        }
        PageHelper.startPage(userQuery.getPage(), userQuery.getLimit());
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectList(queryWrapper));
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    @Override
    public double fineOfUser(String userId) {
        QueryWrapper<Borrow> wrapper = new QueryWrapper<>();
        wrapper.eq("reader_id", userId).isNull("return_time");
        List<Borrow> borrows = borrowMapper.selectList(wrapper);
        //查询借阅日期大于30的
        double fine = 0;
        for (Borrow borrow : borrows) {
            if (borrow.getFine() > 0) {
                fine += borrow.getFine();
            }
        }
        if (fine > 0) {
            UserMsg userMsg = new UserMsg();
            userMsg.setUserId(userId);
            userMsg.setMsg("您有罚款尚未缴清，请到图书管理员处缴纳！");
            userMsgMapper.insert(userMsg);
            User user = userMapper.selectById(userId).setStatus(UserStatusEnum.暂停借阅.getCode());
            userMapper.updateById(user);
        }
        return fine;
    }
}
