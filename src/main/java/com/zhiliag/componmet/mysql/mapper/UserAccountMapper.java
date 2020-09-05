package com.zhiliag.componmet.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhiliag.componmet.mysql.entity.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author:lizhi
 * @Date: 2020/9/2
 * @des:
 **/
@Mapper
@Repository
public interface UserAccountMapper extends BaseMapper<UserAccount> {

    /**
     * 根据账户主键id查询
     * @param id
     * @return
     */
    @Select("select * from user_account where id=#{id}")
    UserAccount selectByAccountId(Integer id);

    /**
     * 根据用户id查询
     * @param userId
     * @return
     */
    @Select("select * from user_account where user_id=#{userId} and active=1")
    UserAccount selectByUserId(@Param("userId")Integer userId);

    /**
     * 根据账户余额
     * @param money
     * @param userId
     * @return
     */
    @Update("update  user_account set amount=amount-#{money} where user_id=#{userId} and active=1")
    int updateAmount(@Param("money") Double money,@Param("userId") Integer userId);

    @Update("update  user_account set amount=amount-#{money},version=version+1 where user_id=#{userId} and version=#{version}" +
            " and amount>0 and amount-#{money}>=0")
    int updateAmountByVersion(@Param("money") Double money,@Param("userId")Integer userId,@Param("version")Integer version);

    @Select("select * from user_account where user_id=#{userId} and active=1 for update")
    UserAccount selectByUserIdWithPesLocking(@Param("userId")Integer userId);

    @Update("update user_account set amount=amount-#{money} where user_id=#{userId} and amount>0 and amount-#{money}>0")
    int updateAmountByPesLocking(@Param("money") Double money,@Param("userId")Integer userId);
}
