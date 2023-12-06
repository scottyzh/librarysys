package com.gdufe.libsys.enums;

import lombok.Getter;

@Getter

public enum UserStatusEnum {

    正常(0),

    挂失(1),

    注销(2),

    暂停借阅(3),

    申请挂失(4),

    ;

    Integer code;

    UserStatusEnum(Integer code) {
        this.code = code;
    }
}
