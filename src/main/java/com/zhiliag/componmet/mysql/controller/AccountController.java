package com.zhiliag.componmet.mysql.controller;


import com.zhiliag.componmet.bean.BaseResponse;
import com.zhiliag.componmet.mysql.service.IUserAccountService;
import com.zhiliag.componmet.mysql.vo.UserAccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:lizhi
 * @Date: 2020/9/2
 * @des:
 **/
@RestController
@Slf4j
public class AccountController {

    @Autowired
    IUserAccountService iUserAccountService;


    @PostMapping("/money/take")
    public BaseResponse takeMoney(@RequestBody UserAccountDto userAccountDto){
        if (StringUtils.isEmpty(userAccountDto.getMoney())|| StringUtils.isEmpty(userAccountDto.getUserId())){
            return BaseResponse.fail(500,"参数为空");
        }
//        iUserAccountService.takeMoney(userAccountDto);
//        return iUserAccountService.takeMoneyWithLock(userAccountDto);
        return iUserAccountService.takeMoneyWithPesLocking(userAccountDto);
    }
}
