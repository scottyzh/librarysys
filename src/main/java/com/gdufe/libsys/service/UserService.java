package com.gdufe.libsys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdufe.libsys.entity.User;
import com.gdufe.libsys.query.UserQuery;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jonil
 * @since 2021-11-23
 */
public interface UserService extends IService<User> {

    //登录
    void login(String username, String userPassword);

    //更新密码
    void updateUserPassword(String userId, String userOldPassword, String newPassword, String confirmPassword);

    //读者更新用户信息
    void updateInfo(String userId, String username, String phone);

    //增加用户
    void addUser(String userId, String userName, String userPassword, String phone, Integer identity);

    //更新用户信息
    void updateUser(String userId, String userName, String userPassword, String phone, Integer identity, Integer status);

    //挂失
    void lossApply(String userId, String password);

    //查询用户
    Map<String, Object> queryUsersByParams(UserQuery userQuery);

    //查询罚款
    double fineOfUser(String userId);
}
