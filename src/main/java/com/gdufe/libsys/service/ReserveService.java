package com.gdufe.libsys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdufe.libsys.entity.Reserve;
import com.gdufe.libsys.query.ReserveQuery;

import java.util.Map;

public interface ReserveService extends IService<Reserve> {
    Map<String, Object> queryReserveListByParams(ReserveQuery reserveQuery);

    void selectBookById(Integer bookId, String userId, Integer reserveId, String operator);

    void book(String readerId, String book_id);

    void remindBook(Integer reserveId);

    void cancelReserve(Integer reserveId);
}
