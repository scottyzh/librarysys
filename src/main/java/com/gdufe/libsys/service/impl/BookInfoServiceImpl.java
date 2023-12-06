package com.gdufe.libsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdufe.libsys.entity.BookInfo;
import com.gdufe.libsys.entity.BookStock;
import com.gdufe.libsys.mapper.BookInfoMapper;
import com.gdufe.libsys.mapper.BookStockMapper;
import com.gdufe.libsys.query.BookInfoQuery;
import com.gdufe.libsys.service.BookInfoService;
import com.gdufe.libsys.utils.AssertUtil;
import com.gdufe.libsys.vo.BookInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jonil
 * @since 2021-11-23
 */
@Service
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfo> implements BookInfoService {

    @Autowired
    BookInfoMapper bookInfoMapper;

    @Autowired
    BookStockMapper bookStockMapper;


    //查询图书列表
    @Override
    public Map<String, Object> queryBookInfosByParams(BookInfoQuery bookInfoQuery) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(bookInfoQuery.getPage(), bookInfoQuery.getLimit());
        PageInfo<BookInfo> pageInfo = new PageInfo<>(getBookInfos(bookInfoQuery));
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    //把图书信息封装成一个方法
    public List<BookInfo> getBookInfos(BookInfoQuery bookInfoQuery) {
        QueryWrapper<BookInfo> queryWrapper = new QueryWrapper<>();
        if (bookInfoQuery.getBookName() != null && !Objects.equals(bookInfoQuery.getBookName(), "")) {
            queryWrapper.like("book_name", bookInfoQuery.getBookName());
        }
        if (bookInfoQuery.getIsbn() != null && !Objects.equals(bookInfoQuery.getIsbn(), "")) {
            queryWrapper.like("isbn", bookInfoQuery.getIsbn());
        }
        if (bookInfoQuery.getAuthor() != null && !Objects.equals(bookInfoQuery.getAuthor(), "")) {
            queryWrapper.like("author", bookInfoQuery.getAuthor());
        }
        if (bookInfoQuery.getPublisher() != null && !Objects.equals(bookInfoQuery.getPublisher(), "")) {
            queryWrapper.like("publisher", bookInfoQuery.getPublisher());
        }
        List<BookInfo> bookInfos = bookInfoMapper.selectList(queryWrapper);
        for (BookInfo bookInfo : bookInfos) {
            QueryWrapper<BookStock> wrapper = new QueryWrapper<>();
            wrapper.eq("isbn", bookInfo.getIsbn());
            List<BookStock> bookStocks = bookStockMapper.selectList(wrapper);
            //计算总库存
            bookInfo.setTotalStock(bookStocks.size());
            int i = 0;//计算未借阅的数数量
            int j = 0;//计算是三水还是广州
            for (BookStock bookStock : bookStocks) {
                if (bookStock.getStatus() == 0) {
                    i++;
                }
                if (bookStock.getBookLocation() == 1) {
                    j++;
                }
            }
            bookInfo.setPresentStock(i);
            if(i == 0){
                bookInfo.setBookLocation(3);
            }else {
                if (j == 0) {
                    bookInfo.setBookLocation(0);
                } else if (j == bookStocks.size()) {
                    bookInfo.setBookLocation(1);
                } else {
                    bookInfo.setBookLocation(2);
                }
            }
            bookInfoMapper.updateById(bookInfo);
        }
        return bookInfos;
    }

    @Override
    public void addBookInfo(String isbn, String bookName, String author, String publisher, Integer categoryId, String bookPic, String bookDescription) {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setIsbn(isbn);
        bookInfo.setBookName(bookName);
        bookInfo.setAuthor(author);
        bookInfo.setPublisher(publisher);
        bookInfo.setCategoryId(categoryId);
        bookInfo.setBookLocation(3);
        bookInfo.setEnterTime(LocalDateTime.now());
        bookInfo.setBookPic(bookPic);
        bookInfo.setBookDescription(bookDescription);
        AssertUtil.isTrue(bookInfoMapper.insert(bookInfo) < 1, 201, "添加书籍失败");
    }

    @Override
    public void updateBookInfo(String isbn, String bookName, String author, String publisher, Integer categoryId) {
        BookInfo bookInfo = bookInfoMapper.selectById(isbn);
        bookInfo.setBookName(bookName);
        bookInfo.setAuthor(author);
        bookInfo.setPublisher(publisher);
        bookInfo.setCategoryId(categoryId);
        bookInfo.setUpdateTime(LocalDateTime.now());
        AssertUtil.isTrue(bookInfoMapper.updateById(bookInfo) < 1, 201, "编辑图书信息失败");
    }

    @Override
    public void stopBookBorrw(String isbn) {
        BookInfo bookInfo = bookInfoMapper.selectById(isbn);

        if (bookInfo.getStatus() == 0) {
            bookInfo.setStatus(1);
        } else if (bookInfo.getStatus() == 1) {
            bookInfo.setStatus(0);
        }
        bookInfoMapper.updateById(bookInfo);
    }

    @Override
    public BookInfoVo findDetail(String isbn) {
        BookInfoVo bookInfoVo = new BookInfoVo();
        BookInfo bookInfo = bookInfoMapper.selectById(isbn);
        BeanUtils.copyProperties(bookInfo, bookInfoVo);
        bookInfoVo.setCategoryName(getCategoryName(bookInfo.getCategoryId()));
        return bookInfoVo;
    }

    public String getCategoryName(Integer categoryId) {
        if (categoryId == 1) {
            return "马克思主义";
        } else if (categoryId == 2) {
            return "哲学、宗教";
        } else if (categoryId == 3) {
            return "社会科学理论";
        } else if (categoryId == 4) {
            return "政治、法律";
        } else if (categoryId == 5) {
            return "军事";
        } else if (categoryId == 6) {
            return "经济";
        } else if (categoryId == 7) {
            return "文化、科学、教育、体育";
        } else if (categoryId == 8) {
            return "语言、文字";
        } else if (categoryId == 9) {
            return "文学";
        } else if (categoryId == 10) {
            return "艺术";
        } else if (categoryId == 11) {
            return "历史、地理";
        } else if (categoryId == 12) {
            return "自然科学总论";
        } else if (categoryId == 13) {
            return "数理科学与化学";
        } else if (categoryId == 14) {
            return "天文学、地球科学";
        } else if (categoryId == 15) {
            return "生物科学";
        } else if (categoryId == 16) {
            return "医药、卫生";
        } else if (categoryId == 17) {
            return "农业科学";
        } else if (categoryId == 18) {
            return "工业技术";
        } else if (categoryId == 19) {
            return "交通运输";
        } else if (categoryId == 20) {
            return "航空、航天";
        } else if (categoryId == 21) {
            return "环境科学、安全科学";
        } else if (categoryId == 22) {
            return "综合性图书";
        }
        return null;
    }
}
