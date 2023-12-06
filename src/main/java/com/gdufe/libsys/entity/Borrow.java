package com.gdufe.libsys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author jonil
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Borrow implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 借阅号
     */
    @TableId(value = "borrow_id", type = IdType.AUTO)
    private Integer borrowId;

    /**
     * 书Id
     */
    private Integer bookId;

    /**
     * 读者Id
     */
    private String readerId;

    /**
     * 借书时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime borrowTime;

    /**
     * 还书时间
     */
    private LocalDateTime returnTime;

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
