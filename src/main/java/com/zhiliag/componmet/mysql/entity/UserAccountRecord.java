package com.zhiliag.componmet.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author:lizhi
 * @Date: 2020/9/2
 * @des:
 **/
@Data
@ToString
public class UserAccountRecord {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer accountId;

    private BigDecimal money;

    private Date createTime;
}
