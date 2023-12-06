package com.gdufe.libsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdufe.libsys.entity.BookInfo;
import com.gdufe.libsys.entity.BookRank;
import com.gdufe.libsys.mapper.BookInfoMapper;
import com.gdufe.libsys.mapper.BookRankMapper;
import com.gdufe.libsys.query.RankQuery;
import com.gdufe.libsys.service.BookRankService;
import com.gdufe.libsys.vo.BookRankVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jonil
 * @since 2021-12-03
 */
@Service
public class BookRankServiceImpl extends ServiceImpl<BookRankMapper, BookRank> implements BookRankService {

    @Resource
    private BookInfoMapper bookInfoMapper;

    @Resource
    private BookRankMapper bookRankMapper;


    //排行榜
    @Override
    public Map<String, Object> queryRankListByParams(RankQuery rankQuery) {
        Map<String, Object> map = new HashMap<>();
        List<BookRank> bookRankList = bookRankMapper.selectList(null);
        ArrayList<BookRankVo> bookRankVoArrayList = new ArrayList<>();
        HashSet<String> isbnList = new HashSet<>();

        for (BookRank bookRank : bookRankList) {
            isbnList.add(bookRank.getIsbn());
        }
        for (String isbn : isbnList) {
            BookRankVo bookRankVo = new BookRankVo();
            QueryWrapper<BookInfo> wrapper = new QueryWrapper<BookInfo>().eq("isbn", isbn);
            BookInfo bookInfo = bookInfoMapper.selectOne(wrapper);
            BeanUtils.copyProperties(bookInfo, bookRankVo);
            QueryWrapper<BookRank> queryWrapper = new QueryWrapper<BookRank>().eq("isbn", isbn);
            Integer amount = bookRankMapper.selectList(queryWrapper).size();
            bookRankVo.setBorrowTimesInThreeMonths(amount);

            bookRankVoArrayList.add(bookRankVo);
        }
        //选择作者
        if (rankQuery.getAuthor() != null && !Objects.equals(rankQuery.getAuthor(), "")) {
            bookRankVoArrayList.removeIf(bookRankVo -> !bookRankVo.getAuthor().contains(rankQuery.getAuthor()));
        }
        //升序，降序
        if (rankQuery.getOrder() != null && rankQuery.getOrder() == 2) {
            bookRankVoArrayList.sort(Comparator.comparing(BookRankVo::getBorrowTimesInThreeMonths));
        } else {
            bookRankVoArrayList.sort(Comparator.comparing(BookRankVo::getBorrowTimesInThreeMonths).reversed());
        }
        PageHelper.startPage(rankQuery.getPage(), rankQuery.getLimit());
        PageInfo<BookRankVo> pageInfo = new PageInfo<>(bookRankVoArrayList);
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }
}
