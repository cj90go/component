package com.zhiliag.componmet.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhiliag.componmet.mysql.entity.UserAccountRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author:lizhi
 * @Date: 2020/9/2
 * @des: 账户余额记录表
 **/
@Mapper
@Repository
public interface UserAccountRecordMapper extends BaseMapper<UserAccountRecord> {

}
