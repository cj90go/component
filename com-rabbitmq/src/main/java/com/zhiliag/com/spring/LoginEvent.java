package com.zhiliag.com.spring;

import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * @author:lizhi
 * @Date: 2020/9/12
 * @des:
 **/
@ToString
public class LoginEvent extends ApplicationEvent {

    private String ip;

    private String name;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LoginEvent(Object source) {
        super(source);
    }

    public LoginEvent(Object source, String ip, String name) {
        super(source);
        this.ip = ip;
        this.name = name;
    }
}
