package com.gdufe.libsys.query;

import lombok.Data;


@Data
public class RankQuery extends BaseQuery {
    //排序依据:作者
    private String author;
    //排序方式
    private Integer order;
}
