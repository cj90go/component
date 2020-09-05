package com.zhiliag.componmet.mysql.service;


import com.zhiliag.componmet.bean.BaseResponse;
import com.zhiliag.componmet.mysql.vo.UserAccountDto;

/**
 * @author:lizhi
 * @Date: 2020/9/3
 * @des:
 **/
public interface IUserAccountService {

    void takeMoney(UserAccountDto userAccountDto);

    BaseResponse takeMoneyWithLock(UserAccountDto userAccountDto);

    BaseResponse takeMoneyWithPesLocking(UserAccountDto userAccountDto);
}
