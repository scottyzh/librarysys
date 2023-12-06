package com.gdufe.libsys.component;

import org.springframework.stereotype.Component;

//系统的统计模块
@Component
public class Statistic {
    //图书馆借阅次数
    private static Integer libBorrowTimes = 0;
    //图书馆预约次数
    private static Integer libReverseTimes = 0;
    //图书馆登录次数
    private static Integer libLoginTimes = 0;

    public static void borrow() {
        libBorrowTimes++;
    }

    public static void reverse() {
        libReverseTimes++;
    }

    public static void login() {
        libLoginTimes++;
    }

    public static Integer getLibBorrowTimes() {
        return libBorrowTimes;
    }

    public static Integer getLibReverseTimes() {
        return libReverseTimes;
    }

    public static Integer getLibLoginTimes() {
        return libLoginTimes;
    }

    public Statistic getInstance() {
        return new Statistic();
    }
}
