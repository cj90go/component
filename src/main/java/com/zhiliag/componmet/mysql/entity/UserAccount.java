package com.zhiliag.componmet.mysql.entity;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author:lizhi
 * @Date: 2020/9/2
 * @des: 用户账户实体类
 **/
@Data
@ToString
public class UserAccount {

    private Integer id;

    private Integer userId;

    private BigDecimal amount;

    private Integer version;

    private Byte active;
}
