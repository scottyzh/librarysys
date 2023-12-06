package com.gdufe.libsys.enums;

import lombok.Getter;

@Getter

public enum ReserveStatusEnum {

    申请借阅中(0),

    已取书(1),

    预约失效(2),


    ;

    Integer code;

    ReserveStatusEnum(Integer code) {
        this.code = code;
    }
}
