package com.gdufe.libsys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdufe.libsys.entity.Borrow;
import com.gdufe.libsys.query.BorrowQuery;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jonil
 * @since 2021-11-23
 */
public interface BorrowService extends IService<Borrow> {

    void borrow(String readerId, Integer bookId, String userId);

    void book(String userId, String isbn);

    void giveback(Integer borrowId);

    void urgereturn(Integer borrowId);

    void renewBorrow(Integer borrowId);

    Map<String, Object> queryBorrowsByParams(BorrowQuery borrowQuery);

}
