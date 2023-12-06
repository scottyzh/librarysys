package com.gdufe.libsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdufe.libsys.entity.*;
import com.gdufe.libsys.enums.BorrowStatusEnum;
import com.gdufe.libsys.enums.ReserveStatusEnum;
import com.gdufe.libsys.mapper.*;
import com.gdufe.libsys.query.BookInfoQuery;
import com.gdufe.libsys.query.ReserveQuery;
import com.gdufe.libsys.service.ReserveService;
import com.gdufe.libsys.utils.AssertUtil;
import com.gdufe.libsys.vo.ReserveVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReserveServiceImpl extends ServiceImpl<ReserveMapper, Reserve> implements ReserveService {

    @Autowired
    private ReserveMapper reserveMapper;

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private BookStockMapper bookStockMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookInfoServiceImpl bookInfoService;

    @Autowired
    private BookRankMapper bookRankMapper;

    @Resource
    private BookInfoMapper bookInfoMapper;

    @Resource
    private UserMsgMapper userMsgMapper;


    //拉取查询列表
    @Override
    public Map<String, Object> queryReserveListByParams(ReserveQuery reserveQuery) {
        Map<String, Object> map = new HashMap<>();
        List<BookInfo> bookInfos = bookInfoService.getBookInfos(new BookInfoQuery());
        List<Reserve> reserves = queryReserve(reserveQuery);
        ArrayList<ReserveVo> reserveVos = new ArrayList<>();
        for (Reserve reserve : reserves) {
            for (BookInfo bookInfo : bookInfos) {
                if (reserve.getIsbn().equals(bookInfo.getIsbn())) {
                    ReserveVo reserveVo = new ReserveVo();
                    BeanUtils.copyProperties(bookInfo, reserveVo);
                    BeanUtils.copyProperties(reserve, reserveVo);
                    reserveVos.add(reserveVo);
                    break;
                }
            }
        }
        PageInfo<Reserve> pageInfo = new PageInfo<>(reserves);
        PageInfo<ReserveVo> pageInfo1 = new PageInfo<>(reserveVos);
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo1.getList());
        return map;
    }

    //选择预约书籍
    @Override
    public void selectBookById(Integer bookId, String readerId, Integer reserveId, String operator) {
        Reserve reserve = reserveMapper.selectById(reserveId);
        AssertUtil.isTrue(reserve.getStatus() == 1, "该预约已经处理");
        AssertUtil.isTrue(reserve.getStatus() == 2, "该预约已经失效");
        //查看借阅是否达到上限
        QueryWrapper<Borrow> borrowQueryWrapper = new QueryWrapper<>();
        borrowQueryWrapper.eq("reader_id", readerId).eq("status", 0);
        List<Borrow> borrowList = borrowMapper.selectList(borrowQueryWrapper);
        User user = userMapper.selectById(readerId);
        int size = borrowList.size();
        Integer identity = user.getIdentity();
        AssertUtil.isTrue(size == 5 && (identity == 0 || identity == 2), "借阅数量达到上限");
        AssertUtil.isTrue(size == 20 && identity == 1, "借阅数量达到上限");
        //填充Rank表
        BookRank bookRank = new BookRank();
        bookRank.setBookId(bookId);
        bookRank.setIsbn(bookStockMapper.selectById(bookId).getIsbn());
        bookRank.setReaderId(readerId);
        bookRankMapper.insert(bookRank);
        //填充borrow
        Borrow borrow = new Borrow();
        borrow.setBookId(bookId);
        borrow.setReaderId(readerId);
        //操作员
        borrow.setOperator(userMapper.selectById(operator).getUserName());
        borrow.setStatus(BorrowStatusEnum.已借未还.getCode());
        borrowMapper.insert(borrow);
        QueryWrapper<BookStock> bookStockQueryWrapper = new QueryWrapper<>();
        bookStockQueryWrapper.eq("book_id", bookId);
        BookStock bookStock = bookStockMapper.selectOne(bookStockQueryWrapper);
        bookStock.setStatus(1);
        bookStockMapper.updateById(bookStock);
        reserve.setStatus(ReserveStatusEnum.已取书.getCode());
        reserveMapper.updateById(reserve);
    }

    //预约图书
    @Override
    public void book(String readerId, String isbn) {
        User user = userMapper.selectById(readerId);
        QueryWrapper<Borrow> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("reader_id", readerId).eq("status", 0);
        List<Borrow> borrows = borrowMapper.selectList(queryWrapper1);
        for (Borrow borrow : borrows) {
            BookStock bookStock = bookStockMapper.selectById(borrow.getBookId());
            AssertUtil.isTrue(bookStock.getIsbn().equals(isbn), "读者已经借阅了该书！请勿重复预约");
        }
        QueryWrapper<Reserve> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("reader_id", readerId).eq("status", 0);
        List<Reserve> reserves = reserveMapper.selectList(queryWrapper);
        for (Reserve reserve : reserves) {
            AssertUtil.isTrue(reserve.getIsbn().equals(isbn), "读者已经预约了该书！请勿重复预约");
        }
        AssertUtil.isTrue(user == null, "不存在该用户");
        BookInfo bookInfo = bookInfoMapper.selectById(isbn);
        AssertUtil.isTrue(bookInfo.getStatus() == 1, "该书暂停借阅！");
        Reserve reserve = new Reserve();
        reserve.setReaderId(readerId);
        reserve.setIsbn(isbn);
        reserve.setStatus(0);
        reserve.setReaderIdentity(user.getIdentity());
        reserveMapper.insert(reserve);
    }

    //提醒取书
    @Override
    public void remindBook(Integer reserveId) {
        QueryWrapper<Reserve> queryWrapper = new QueryWrapper<Reserve>().eq("reserve_id", reserveId);
        Reserve reserve = reserveMapper.selectOne(queryWrapper);
        BookInfo bookInfo = bookInfoMapper.selectById(reserve.getIsbn());
        AssertUtil.isTrue(reserve.getStatus() == 1, "该预约已经处理");
        AssertUtil.isTrue(reserve.getStatus() == 2, "该预约已经失效");
        UserMsg userMsg = new UserMsg();
        userMsg.setMsg("您预约的书籍《" + bookInfo.getBookName() + "》现在可以到图书馆领取！");
        userMsg.setUserId(reserve.getReaderId());
        userMsg.setCreateTime(LocalDateTime.now());
        userMsgMapper.insert(userMsg);
    }

    //取消预约
    @Override
    public void cancelReserve(Integer reserveId) {
        Reserve reserve = reserveMapper.selectById(reserveId);
        AssertUtil.isTrue(reserve.getStatus() == 2, "请勿重复取消！");
        reserve.setStatus(2);
        reserveMapper.updateById(reserve);
    }

    //查询预约
    public List<Reserve> queryReserve(ReserveQuery reserveQuery) {
        QueryWrapper<Reserve> queryWrapper = new QueryWrapper<>();
        if (reserveQuery.getReserveId() != null) {
            queryWrapper.eq("reserve_id", reserveQuery.getReserveId());
        }
        if (reserveQuery.getStatus() != null) {
            queryWrapper.eq("status", reserveQuery.getStatus());
        }
        if (reserveQuery.getBookId() != null) {
            queryWrapper.eq("book_id", reserveQuery.getBookId());
        }
        if (reserveQuery.getReaderIdentity() != null) {
            queryWrapper.eq("reader_identity", reserveQuery.getReaderIdentity());
        }
        if (reserveQuery.getIsbn() != null && !Objects.equals(reserveQuery.getIsbn(), "")) {
            queryWrapper.like("isbn", reserveQuery.getIsbn());
        }
        if (reserveQuery.getReaderId() != null && !Objects.equals(reserveQuery.getReaderId(), "")) {
            queryWrapper.eq("reader_id", reserveQuery.getReaderId());
        }
        PageHelper.startPage(reserveQuery.getPage(), reserveQuery.getLimit());
        return reserveMapper.selectList(queryWrapper);
    }
}
