package com.zhiliag.com.controller;

import com.zhiliag.com.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:lizhi
 * @Date: 2020/9/15
 * @des:
 **/
@RestController
@Slf4j
@CrossOrigin
public class LoginController {

    @PostMapping("/login")
    public String login(@RequestBody LoginVO loginVO){
       log.info("登录信息:{}",loginVO);
       return "success";
    }
}
