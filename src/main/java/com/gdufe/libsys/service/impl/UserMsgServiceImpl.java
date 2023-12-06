package com.gdufe.libsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdufe.libsys.entity.UserMsg;
import com.gdufe.libsys.mapper.UserMsgMapper;
import com.gdufe.libsys.query.UserMsgQuery;
import com.gdufe.libsys.service.UserMsgService;
import com.gdufe.libsys.vo.UserMsgVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jonil
 * @since 2021-12-03
 */
@Service
public class UserMsgServiceImpl extends ServiceImpl<UserMsgMapper, UserMsg> implements UserMsgService {

    @Resource
    private UserMsgMapper userMsgMapper;

    @Override
    public Map<String, Object> getMsg(UserMsgQuery userMsgQuery) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(userMsgQuery.getPage(), userMsgQuery.getLimit());

        List<UserMsg> userMsgList = userMsgMapper.selectList(new QueryWrapper<UserMsg>().eq("user_id", userMsgQuery.getUserId()));
        ArrayList<UserMsgVo> userMsgVoArrayList = new ArrayList<>();
        for (UserMsg userMsg : userMsgList) {
            UserMsgVo userMsgVO = new UserMsgVo();
            BeanUtils.copyProperties(userMsg, userMsgVO);
            userMsgVO.setCreateTime(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒").format(userMsg.getCreateTime()));
            userMsgVoArrayList.add(userMsgVO);
        }
        Collections.reverse(userMsgVoArrayList);
        PageInfo<UserMsgVo> pageInfo = new PageInfo<>(userMsgVoArrayList);
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }
}
