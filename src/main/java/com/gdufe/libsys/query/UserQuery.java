package com.gdufe.libsys.query;

import lombok.Data;

@Data
public class UserQuery extends BaseQuery {
    //用户ID
    private String userId;
    //用户姓名
    private String userName;
    //用户状态
    private String status;
}
