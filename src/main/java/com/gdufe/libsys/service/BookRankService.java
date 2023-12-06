package com.gdufe.libsys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdufe.libsys.entity.BookRank;
import com.gdufe.libsys.query.RankQuery;

import java.util.Map;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jonil
 * @since 2021-12-03
 */
public interface BookRankService extends IService<BookRank> {
    Map<String, Object> queryRankListByParams(RankQuery rankQuery);
}
