package com.zhiliag.componmet.redis.controller;

import com.zhiliag.componmet.bean.BaseResponse;
import com.zhiliag.componmet.redis.service.IUserRegService;
import com.zhiliag.componmet.redis.vo.UserRegVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:lizhi
 * @Date: 2020/9/5
 * @des: 用户注册
 **/
@RestController
@RequestMapping("redis")
@Slf4j
public class UserRegController {

    @Autowired
    IUserRegService iUserRegService;

    /**
     * 用户信息注册
     * 1.校验信息是否为空
     * 2.
     *
     * @param userRegVo
     * @return
     */
    @PostMapping("/userInfo/insert")
    public BaseResponse insertUserInfo(@RequestBody UserRegVo userRegVo) {
        if (StringUtils.isEmpty(userRegVo.getPassword()) || StringUtils.isEmpty(userRegVo.getUserName())) {
            return BaseResponse.fail(500, "注册的用户/密码不能为空");
        }
//        return iUserRegService.insertUserInfoNoLock(userRegVo);//未加锁
        return iUserRegService.insertUserInfoWithLock(userRegVo);//加锁
    }
}
