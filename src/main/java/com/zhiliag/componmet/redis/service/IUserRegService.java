package com.zhiliag.componmet.redis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiliag.componmet.bean.BaseResponse;
import com.zhiliag.componmet.redis.entity.UserReg;
import com.zhiliag.componmet.redis.vo.UserRegVo;

/**
 * @author:lizhi
 * @Date: 2020/9/5
 * @des:
 **/
public interface IUserRegService extends IService<UserReg> {

    BaseResponse insertUserInfoNoLock(UserRegVo userRegVo);

    BaseResponse insertUserInfoWithLock(UserRegVo userRegVo);
}
