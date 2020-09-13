package com.zhiliag.componmet.redis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author:lizhi
 * @Date: 2020/9/5
 * @des: 用户注册信息表
 **/
@Data
public class UserReg {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String password;

    private Date createTime;
}
