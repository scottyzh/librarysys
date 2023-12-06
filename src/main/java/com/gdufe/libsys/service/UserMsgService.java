package com.gdufe.libsys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdufe.libsys.entity.UserMsg;
import com.gdufe.libsys.query.UserMsgQuery;

import java.util.Map;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jonil
 * @since 2021-12-03
 */
public interface UserMsgService extends IService<UserMsg> {
    //查找信息
    Map<String, Object> getMsg(UserMsgQuery userMsgQuery);
}
