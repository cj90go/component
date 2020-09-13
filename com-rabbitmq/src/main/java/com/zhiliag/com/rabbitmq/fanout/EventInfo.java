package com.zhiliag.com.rabbitmq.fanout;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:lizhi
 * @Date: 2020/9/12
 * @des:
 **/
@Data
public class EventInfo implements Serializable {

    private static final long serialVersionUID = -7128203829971899888L;


    private Integer id;

    private String module;

    private String name;

    private String desc;

    public EventInfo(Integer id, String module, String name, String desc) {
        this.id = id;
        this.module = module;
        this.name = name;
        this.desc = desc;
    }

    public EventInfo() {
    }
}
