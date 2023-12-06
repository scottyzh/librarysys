package com.gdufe.libsys.enums;

import lombok.Getter;

@Getter

public enum BorrowStatusEnum {

    已借未还(0),

    已还(1),

    预约未拿(2),

    ;

    Integer code;

    BorrowStatusEnum(Integer code) {
        this.code = code;
    }
}
