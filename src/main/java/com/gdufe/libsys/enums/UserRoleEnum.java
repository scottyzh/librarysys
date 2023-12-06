package com.gdufe.libsys.enums;

import lombok.Getter;

@Getter

public enum UserRoleEnum {
    //0学生，1老师，2图书管理员，3系统管理员
    学生(0),

    老师(1),

    图书管理员(2),

    系统管理员(3),

    ;

    Integer code;

    UserRoleEnum(Integer code) {
        this.code = code;
    }
}
