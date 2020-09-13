package com.zhiliag.componmet.redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhiliag.componmet.redis.entity.UserReg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author:lizhi
 * @Date: 2020/9/5
 * @des:
 **/
@Repository
@Mapper
public interface UserRegMapper extends BaseMapper<UserReg> {
}
