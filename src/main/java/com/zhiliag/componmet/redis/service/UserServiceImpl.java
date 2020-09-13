package com.zhiliag.componmet.redis.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiliag.componmet.bean.BaseResponse;
import com.zhiliag.componmet.redis.entity.UserReg;
import com.zhiliag.componmet.redis.mapper.UserRegMapper;
import com.zhiliag.componmet.redis.vo.UserRegVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:lizhi
 * @Date: 2020/9/5
 * @des: 用户信息注册
 * 1.查询用户名是否已经被注册
 * 2.数据落地
 **/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserRegMapper, UserReg> implements IUserRegService {

    @Autowired
    UserRegMapper userRegMapper;

    /**
     * 不加锁下实现用户信息注册功能
     * 1.查询用户名是否已经被注册
     * 2.数据落地
     *
     * @param userRegVo
     * @return
     */
    @Override
    public BaseResponse insertUserInfoNoLock(UserRegVo userRegVo) {
        return null;
    }

    /**
     * 枷锁下实现用户信息注册
     *
     * @param userRegVo
     * @return
     */
    @Override
    public BaseResponse insertUserInfoWithLock(UserRegVo userRegVo) {
        UserReg userReg = userRegMapper.selectOne(new QueryWrapper<UserReg>().lambda().eq(UserReg::getUserName, userRegVo.getUserName()));
        if (userReg != null) {
            return BaseResponse.fail(500, "当前用户名已被注册");
        }

        return null;
    }
}
