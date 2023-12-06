package com.gdufe.libsys.utils;


import com.gdufe.libsys.exceptions.ParamsException;

public class AssertUtil {

    //为空则抛出异常
    public static void isTrue(Boolean flag, Integer code, String msg) {
        if (flag) {
            throw new ParamsException(code, msg);
        }
    }

    public static void isTrue(boolean flag, String msg) {
        if (flag) {
            throw new ParamsException(msg);
        }
    }
}
