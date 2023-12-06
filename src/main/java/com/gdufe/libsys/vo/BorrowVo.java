package com.gdufe.libsys.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BorrowVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 借阅号
     */
    private Integer borrowId;

    /**
     * 书Id
     */
    private Integer bookId;

    /**
     * 读者Id
     */
    private String readerId;


    private String bookName;

    /**
     * 借书时间
     */
    private LocalDateTime borrowTime;

    /**
     * 还书时间
     */
    private LocalDateTime returnTime;

    /**
     * 还书时间
     */
    private LocalDateTime shouldReturnTime;

    /**
     * 操作员
     */
    private String operator;

    /**
     * 0时未存在罚款，大于0时存在罚款
     */
    private Double fine;

    /**
     * 0已借未还，1已还，2预约未拿
     */
    private Integer status;

    /**
     * 0无续借，1当前续借状态
     */
    private Integer renew;

}
