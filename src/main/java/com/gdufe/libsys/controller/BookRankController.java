package com.gdufe.libsys.controller;


import com.gdufe.libsys.query.RankQuery;
import com.gdufe.libsys.service.BookRankService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jonil
 * @since 2021-12-03
 */
@Controller
@RequestMapping("/bookRank")
public class BookRankController extends BaseController {

    @Resource
    private BookRankService bookRankService;

    //返回图书排行榜
    @GetMapping("/toRankPage")
    public String toRankPage() {
        return "book/book_rank";
    }

    //排行榜查询
    @RequestMapping("/rankList")
    @ResponseBody
    public Map<String, Object> queryRankListByParams(RankQuery rankQuery) {
        return bookRankService.queryRankListByParams(rankQuery);
    }
}

